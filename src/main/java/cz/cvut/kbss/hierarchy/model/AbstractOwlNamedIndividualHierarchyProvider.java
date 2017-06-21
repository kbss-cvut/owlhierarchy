package cz.cvut.kbss.hierarchy.model;

import cz.cvut.kbss.hierarchy.util.OwlOntologyClosureObjectPropertyAssertionAxiomsCache;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProviderListener;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public abstract class AbstractOwlNamedIndividualHierarchyProvider implements
    OWLObjectHierarchyProvider<OWLNamedIndividual> {
    protected static final Marker m
        = MarkerFactory.getMarker("IndividualHierarchy");
    protected static final Logger logger
        = LoggerFactory.getLogger(AbstractOwlNamedIndividualHierarchyProvider.class);

    protected boolean inverse;

    protected OWLObjectProperty property;

    private Set<OWLOntology> ontologies;

    private boolean hideOrphans;

    private List<OWLNamedIndividual> roots = new ArrayList<>();

    private Map<OWLNamedIndividual, Set<OWLNamedIndividual>> mapParents = new HashMap<>();

    private Map<OWLNamedIndividual, Set<OWLNamedIndividual>> mapChildren = new HashMap<>();

    private final List<OWLObjectHierarchyProviderListener> listeners = new ArrayList<>();

    protected void compute() {
        if (property == null || ontologies == null) {
            return;
        }

        roots.clear();

        ontologies.forEach((o) -> roots.addAll(o.getIndividualsInSignature()));

        mapParents.clear();
        mapChildren.clear();
        if (logger.isDebugEnabled()) {
            logger.debug(m, "Ontologies ", ontologies);
        }
        for (final OWLOntology ontology : ontologies) {
            if (logger.isDebugEnabled()) {
                logger.debug(m, " - processing ontology {}", ontology);
            }
            final OWLOntology o2
                = new OwlOntologyClosureObjectPropertyAssertionAxiomsCache(ontology);
            for (final OWLNamedIndividual i : o2.getIndividualsInSignature()) {
                if (logger.isDebugEnabled()) {
                    logger.debug(m, "   - processing individual {}", i);
                }
                for (final OWLNamedIndividual niObject : getSubs(i, o2)) {
                    Set<OWLNamedIndividual> children
                        = mapChildren.computeIfAbsent(i, k -> new HashSet<>());
                    children.add(niObject);
                    roots.remove(niObject);
                    Set<OWLNamedIndividual> parents
                        = mapParents.computeIfAbsent(niObject, k -> new HashSet<>());
                    parents.add(i);

                }
            }
        }
        fireChange();
    }

    protected abstract Set<OWLNamedIndividual> getSubs(
        final OWLNamedIndividual i,
        final OWLOntology ontology);

    private void fireChange() {
        logger.trace(m, "Firing change.");
        for (final OWLObjectHierarchyProviderListener l : new ArrayList<>(listeners)) {
            try {
                logger.trace(m, "  - notifying {}", l);
                l.hierarchyChanged();
                logger.trace(m, "  - notified {}", l);
            } catch (Exception e) {
                logger.warn(m, "Error during listener execution, {}, deregistering it.", l);
                logger.warn(m, "  - details", e);
                removeListener(l);
            }
        }
    }

    public void setHideOrphans(boolean hideOrphans) {
        this.hideOrphans = hideOrphans;
        compute();
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
        compute();
    }

    public void setProperty(OWLObjectProperty property) {
        this.property = property;
        compute();
    }

    @Override
    public void setOntologies(Set<OWLOntology> ontologies) {
        logger.trace(m, "Setting ontologies ", ontologies);
        this.ontologies = ontologies;
        compute();
    }

    @Override
    public Set<OWLNamedIndividual> getRoots() {
        logger.trace(m, "Getting roots {}", roots);
        return new HashSet<>(
            hideOrphans ? roots.stream().filter(
                n -> mapChildren.containsKey(n)
            ).collect(Collectors.toSet()) : roots
        );
    }

    @Override
    public Set<OWLNamedIndividual> getChildren(OWLNamedIndividual i) {
        logger.trace(m, "Getting children of {} to {}", i, mapChildren.containsKey(i));
        if (mapChildren.containsKey(i)) {
            return mapChildren.get(i);
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public Set<OWLNamedIndividual> getParents(OWLNamedIndividual i) {
        logger.trace(m, "Getting parents of {} to {}", i, mapParents.containsKey(i));
        if (mapParents.containsKey(i)) {
            return mapParents.get(i);
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public Set<OWLNamedIndividual> getDescendants(OWLNamedIndividual object) {
        logger.trace(m, "Getting descendants of {}", object);
        final Set<OWLNamedIndividual> individuals = new HashSet<>();
        traverseMap(object, mapChildren, individuals);
        return individuals;
    }

    @Override
    public Set<OWLNamedIndividual> getAncestors(OWLNamedIndividual object) {
        logger.trace(m, "Getting ancestors of {}", object);
        final Set<OWLNamedIndividual> individuals = new HashSet<>();
        traverseMap(object, mapParents, individuals);
        return individuals;
    }

    private void traverseMap(
        final OWLNamedIndividual current,
        final Map<OWLNamedIndividual,
            Set<OWLNamedIndividual>> map,
        final Set<OWLNamedIndividual> results) {
        for (final OWLNamedIndividual i : map.get(current)) {
            results.add(i);
            traverseMap(i, map, results);
        }
    }

    @Override
    public Set<OWLNamedIndividual> getEquivalents(OWLNamedIndividual object) {
        logger.trace(m, "Getting equivalents of {}", object);
        final Set<OWLNamedIndividual> s12 = new HashSet<>(getChildren(object));
        s12.retainAll(getParents(object));
        return s12;
    }

    @Override
    public Set<List<OWLNamedIndividual>> getPathsToRoot(
        OWLNamedIndividual object) {
        logger.trace(m, "Getting paths to root of {}", object);

        // TODO
        return Collections.emptySet();
    }

    @Override
    public boolean containsReference(OWLNamedIndividual i) {
        logger.trace(m, "containsReference {}", i);
        return roots.contains(i)
            || mapParents.keySet().contains(i) || mapChildren.keySet().contains(i);
    }

    @Override
    public void addListener(final
                            OWLObjectHierarchyProviderListener<OWLNamedIndividual> l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
            logger.debug(m, "Listener added : {}, number of listeners : {}", l, listeners.size());
        } else {
            logger.warn(m, "Trying to add the listener once more : {}", l);
        }
    }

    @Override
    public void removeListener(final
                               OWLObjectHierarchyProviderListener<OWLNamedIndividual> l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
            logger.debug(m, "Listener removed : {}, number of listeners : {}", l, listeners.size());
        } else {
            logger.warn(m, "Trying to remove a listener that is not registered : {}", l);
        }
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
