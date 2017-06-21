package cz.cvut.kbss.owlhierarchy.util;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nonnull;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPrimitive;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

public class OwlOntologyAdapter implements OWLOntology, Serializable {

    private final OWLOntology ont;

    public OwlOntologyAdapter(final OWLOntology ont) {
        this.ont = ont;
    }

    @Override
    public void accept(@Nonnull OWLNamedObjectVisitor owlNamedObjectVisitor) {
        ont.accept(owlNamedObjectVisitor);
    }

    @Nonnull
    @Override
    public <O> O accept(@Nonnull OWLNamedObjectVisitorEx<O> owlNamedObjectVisitorEx) {
        return ont.accept(owlNamedObjectVisitorEx);
    }

    @Nonnull
    @Override
    public OWLOntologyManager getOWLOntologyManager() {
        return ont.getOWLOntologyManager();
    }

    @Override
    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {

        ont.setOWLOntologyManager(owlOntologyManager);
    }

    @Nonnull
    @Override
    public OWLOntologyID getOntologyID() {
        return ont.getOntologyID();
    }

    @Override
    public boolean isAnonymous() {
        return ont.isAnonymous();
    }

    @Nonnull
    @Override
    public Set<OWLAnnotation> getAnnotations() {
        return ont.getAnnotations();
    }

    @Nonnull
    @Override
    public Set<IRI> getDirectImportsDocuments() {
        return ont.getDirectImportsDocuments();
    }

    @Nonnull
    @Override
    public Set<OWLOntology> getDirectImports() {
        return ont.getDirectImports();
    }

    @Nonnull
    @Override
    public Set<OWLOntology> getImports() {
        return ont.getImports();
    }

    @Nonnull
    @Override
    public Set<OWLOntology> getImportsClosure() {
        return ont.getImportsClosure();
    }

    @Nonnull
    @Override
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return ont.getImportsDeclarations();
    }

    @Override
    public boolean isEmpty() {
        return ont.isEmpty();
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getTBoxAxioms(@Nonnull Imports imports) {
        return ont.getTBoxAxioms(imports);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getABoxAxioms(@Nonnull Imports imports) {
        return ont.getABoxAxioms(imports);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getRBoxAxioms(@Nonnull Imports imports) {
        return ont.getRBoxAxioms(imports);
    }

    @Nonnull
    @Override
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return ont.getGeneralClassAxioms();
    }

    @Nonnull
    @Override
    public Set<OWLEntity> getSignature() {
        return ont.getSignature();
    }

    @Nonnull
    @Override
    public Set<OWLEntity> getSignature(@Nonnull Imports imports) {
        return ont.getSignature(imports);
    }

    @Override
    public boolean isDeclared(@Nonnull OWLEntity owlEntity) {
        return ont.isDeclared(owlEntity);
    }

    @Override
    public boolean isDeclared(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        return ont.isDeclared(owlEntity, imports);
    }

    @Override
    public void saveOntology() throws OWLOntologyStorageException {

        ont.saveOntology();
    }

    @Override
    public void saveOntology(@Nonnull IRI iri) throws OWLOntologyStorageException {

        ont.saveOntology(iri);
    }

    @Override
    public void saveOntology(@Nonnull OutputStream outputStream)
        throws OWLOntologyStorageException {

        ont.saveOntology(outputStream);
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat)
        throws OWLOntologyStorageException {

        ont.saveOntology(owlDocumentFormat);
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat, @Nonnull IRI iri)
        throws OWLOntologyStorageException {

        ont.saveOntology(owlDocumentFormat, iri);
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat,
                             @Nonnull OutputStream outputStream)
        throws OWLOntologyStorageException {

        ont.saveOntology(owlDocumentFormat, outputStream);
    }

    @Override
    public void saveOntology(@Nonnull OWLOntologyDocumentTarget owlOntologyDocumentTarget)
        throws OWLOntologyStorageException {

        ont.saveOntology(owlOntologyDocumentTarget);
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat,
                             @Nonnull OWLOntologyDocumentTarget owlOntologyDocumentTarget)
        throws OWLOntologyStorageException {

        ont.saveOntology(owlDocumentFormat, owlOntologyDocumentTarget);
    }

    @Override
    public int compareTo(OWLObject o) {
        return this.ont.compareTo(o);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        return ont.getAnnotationPropertiesInSignature();
    }

    @Nonnull
    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return ont.getAnonymousIndividuals();
    }

    @Nonnull
    @Override
    public Set<OWLClass> getClassesInSignature() {
        return ont.getClassesInSignature();
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity) {
        return ont.containsEntityInSignature(owlEntity);
    }

    @Nonnull
    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return ont.getDatatypesInSignature();
    }

    @Nonnull
    @Override
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri) {
        return ont.getEntitiesInSignature(iri);
    }

    @Nonnull
    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return ont.getIndividualsInSignature();
    }

    @Nonnull
    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return ont.getObjectPropertiesInSignature();
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getAxioms(@Nonnull Imports imports) {
        return ont.getAxioms(imports);
    }

    @Override
    public int getAxiomCount(@Nonnull Imports imports) {
        return ont.getAxiomCount(imports);
    }

    @Nonnull
    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(@Nonnull Imports imports) {
        return ont.getLogicalAxioms(imports);
    }

    @Override
    public int getLogicalAxiomCount(@Nonnull Imports imports) {
        return ont.getLogicalAxiomCount(imports);
    }

    @Nonnull
    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType,
                                                 @Nonnull Imports imports) {
        return ont.getAxioms(axiomType, imports);
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType,
                                                  @Nonnull Imports imports) {
        return ont.getAxiomCount(axiomType, imports);
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom, @Nonnull Imports imports,
                                 @Nonnull AxiomAnnotations axiomAnnotations) {
        return ont.containsAxiom(owlAxiom, imports, axiomAnnotations);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom,
                                                    @Nonnull Imports imports) {
        return ont.getAxiomsIgnoreAnnotations(owlAxiom, imports);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive,
                                              @Nonnull Imports imports) {
        return ont.getReferencingAxioms(owlPrimitive, imports);
    }

    @Nonnull
    @Override
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass, @Nonnull Imports imports) {
        return ont.getAxioms(owlClass, imports);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression,
        @Nonnull Imports imports) {
        return ont.getAxioms(owlObjectPropertyExpression, imports);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty,
                                               @Nonnull Imports imports) {
        return ont.getAxioms(owlDataProperty, imports);
    }

    @Nonnull
    @Override
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual,
                                             @Nonnull Imports imports) {
        return ont.getAxioms(owlIndividual, imports);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty,
                                             @Nonnull Imports imports) {
        return ont.getAxioms(owlAnnotationProperty, imports);
    }

    @Nonnull
    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype,
                                                     @Nonnull Imports imports) {
        return ont.getAxioms(owlDatatype, imports);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getAxioms(boolean b) {
        return ont.getAxioms(b);
    }

    @Override
    public int getAxiomCount(boolean b) {
        return ont.getAxiomCount(b);
    }

    @Nonnull
    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(boolean b) {
        return ont.getLogicalAxioms(b);
    }

    @Override
    public int getLogicalAxiomCount(boolean b) {
        return ont.getLogicalAxiomCount(b);
    }

    @Nonnull
    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType, boolean b) {
        return ont.getAxioms(axiomType, b);
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType, boolean b) {
        return ont.getAxiomCount(axiomType, b);
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom, boolean b) {
        return ont.containsAxiom(owlAxiom, b);
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom, boolean b) {
        return ont.containsAxiomIgnoreAnnotations(owlAxiom, b);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom, boolean b) {
        return ont.getAxiomsIgnoreAnnotations(owlAxiom, b);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive, boolean b) {
        return ont.getReferencingAxioms(owlPrimitive, b);
    }

    @Nonnull
    @Override
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass, boolean b) {
        return ont.getAxioms(owlClass, b);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression,
        boolean b) {
        return ont.getAxioms(owlObjectPropertyExpression, b);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty,
                                               boolean b) {
        return ont.getAxioms(owlDataProperty, b);
    }

    @Nonnull
    @Override
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual, boolean b) {
        return ont.getAxioms(owlIndividual, b);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty,
                                             boolean b) {
        return ont.getAxioms(owlAnnotationProperty, b);
    }

    @Nonnull
    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype, boolean b) {
        return ont.getAxioms(owlDatatype, b);
    }

    @Override
    public int getAxiomCount() {
        return ont.getAxiomCount();
    }

    @Override
    public int getLogicalAxiomCount() {
        return ont.getLogicalAxiomCount();
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType) {
        return ont.getAxiomCount(axiomType);
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom) {
        return ont.containsAxiomIgnoreAnnotations(owlAxiom);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom) {
        return ont.getAxiomsIgnoreAnnotations(owlAxiom);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive) {
        return ont.getReferencingAxioms(owlPrimitive);
    }

    @Nonnull
    @Override
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass) {
        return ont.getAxioms(owlClass);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        return ont.getAxioms(owlDataProperty);
    }

    @Nonnull
    @Override
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return ont.getAxioms(owlAnnotationProperty);
    }

    @Nonnull
    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype) {
        return ont.getAxioms(owlDatatype);
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getAxioms() {
        return ont.getAxioms();
    }

    @Nonnull
    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType) {
        return ont.getAxioms(axiomType);
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom) {
        return ont.containsAxiom(owlAxiom);
    }

    @Nonnull
    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        return ont.getLogicalAxioms();
    }

    @Nonnull
    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull Class<T> aClass, @Nonnull OWLObject owlObject, @Nonnull Imports imports, @Nonnull Navigation navigation) {
        return ont.getAxioms(aClass, owlObject, imports, navigation);
    }

    @Nonnull
    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(@Nonnull OWLAxiomSearchFilter owlAxiomSearchFilter, @Nonnull Object o, @Nonnull Imports imports) {
        return this.ont.filterAxioms(owlAxiomSearchFilter, o, imports);
    }

    @Override
    public boolean contains(@Nonnull OWLAxiomSearchFilter owlAxiomSearchFilter, @Nonnull Object o, @Nonnull Imports imports) {
        return this.ont.contains(owlAxiomSearchFilter, o, imports);
    }

    @Nonnull
    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull Class<T> aClass, @Nonnull Class<? extends OWLObject> aClass1, @Nonnull OWLObject owlObject, @Nonnull Imports imports, @Nonnull Navigation navigation) {
        return ont.getAxioms(aClass, aClass1, owlObject, imports, navigation);
    }

    @Nonnull
    @Override
    public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return ont.getSubAnnotationPropertyOfAxioms(owlAnnotationProperty);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return ont.getAnnotationPropertyDomainAxioms(owlAnnotationProperty);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return ont.getAnnotationPropertyRangeAxioms(owlAnnotationProperty);
    }

    @Nonnull
    @Override
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(@Nonnull OWLEntity owlEntity) {
        return ont.getDeclarationAxioms(owlEntity);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(@Nonnull OWLAnnotationSubject owlAnnotationSubject) {
        return ont.getAnnotationAssertionAxioms(owlAnnotationSubject);
    }

    @Nonnull
    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(@Nonnull OWLClass owlClass) {
        return ont.getSubClassAxiomsForSubClass(owlClass);
    }

    @Nonnull
    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(@Nonnull OWLClass owlClass) {
        return ont.getSubClassAxiomsForSuperClass(owlClass);
    }

    @Nonnull
    @Override
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(@Nonnull OWLClass owlClass) {
        return ont.getEquivalentClassesAxioms(owlClass);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(@Nonnull OWLClass owlClass) {
        return ont.getDisjointClassesAxioms(owlClass);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(@Nonnull OWLClass owlClass) {
        return ont.getDisjointUnionAxioms(owlClass);
    }

    @Nonnull
    @Override
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(@Nonnull OWLClass owlClass) {
        return ont.getHasKeyAxioms(owlClass);
    }

    @Nonnull
    @Override
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getObjectSubPropertyAxiomsForSubProperty(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getObjectSubPropertyAxiomsForSuperProperty(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getObjectPropertyDomainAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getObjectPropertyRangeAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getInverseObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getEquivalentObjectPropertiesAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getDisjointObjectPropertiesAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getFunctionalObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getInverseFunctionalObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getSymmetricObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getAsymmetricObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getReflexiveObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getIrreflexiveObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return ont.getTransitiveObjectPropertyAxioms(owlObjectPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(@Nonnull OWLDataProperty owlDataProperty) {
        return ont.getDataSubPropertyAxiomsForSubProperty(owlDataProperty);
    }

    @Nonnull
    @Override
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(@Nonnull OWLDataPropertyExpression owlDataPropertyExpression) {
        return ont.getDataSubPropertyAxiomsForSuperProperty(owlDataPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        return ont.getDataPropertyDomainAxioms(owlDataProperty);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        return ont.getDataPropertyRangeAxioms(owlDataProperty);
    }

    @Nonnull
    @Override
    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        return ont.getEquivalentDataPropertiesAxioms(owlDataProperty);
    }

    @Nonnull
    @Override
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        return ont.getDisjointDataPropertiesAxioms(owlDataProperty);
    }

    @Nonnull
    @Override
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(@Nonnull OWLDataPropertyExpression owlDataPropertyExpression) {
        return ont.getFunctionalDataPropertyAxioms(owlDataPropertyExpression);
    }

    @Nonnull
    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getClassAssertionAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(@Nonnull OWLClassExpression owlClassExpression) {
        return ont.getClassAssertionAxioms(owlClassExpression);
    }

    @Nonnull
    @Override
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getDataPropertyAssertionAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getObjectPropertyAssertionAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getNegativeObjectPropertyAssertionAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getNegativeDataPropertyAssertionAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getSameIndividualAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(@Nonnull OWLIndividual owlIndividual) {
        return ont.getDifferentIndividualAxioms(owlIndividual);
    }

    @Nonnull
    @Override
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(@Nonnull OWLDatatype owlDatatype) {
        return ont.getDatatypeDefinitions(owlDatatype);
    }

    @Nonnull
    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        return ont.getNestedClassExpressions();
    }

    @Override
    public void accept(@Nonnull OWLObjectVisitor owlObjectVisitor) {

        ont.accept(owlObjectVisitor);
    }

    @Nonnull
    @Override
    public <O> O accept(@Nonnull OWLObjectVisitorEx<O> owlObjectVisitorEx) {
        return ont.accept(owlObjectVisitorEx);
    }

    @Override
    public boolean isTopEntity() {
        return ont.isTopEntity();
    }

    @Override
    public boolean isBottomEntity() {
        return ont.isBottomEntity();
    }

    @Nonnull
    @Override
    public Set<OWLClass> getClassesInSignature(@Nonnull Imports imports) {
        return ont.getClassesInSignature(imports);
    }

    @Nonnull
    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(@Nonnull Imports imports) {
        return ont.getObjectPropertiesInSignature(imports);
    }

    @Nonnull
    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(@Nonnull Imports imports) {
        return ont.getDataPropertiesInSignature(imports);
    }

    @Nonnull
    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(@Nonnull Imports imports) {
        return ont.getIndividualsInSignature(imports);
    }

    @Nonnull
    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(@Nonnull Imports imports) {
        return ont.getReferencedAnonymousIndividuals(imports);
    }

    @Nonnull
    @Override
    public Set<OWLDatatype> getDatatypesInSignature(@Nonnull Imports imports) {
        return ont.getDatatypesInSignature(imports);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(@Nonnull Imports imports) {
        return ont.getAnnotationPropertiesInSignature(imports);
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        return ont.containsEntityInSignature(owlEntity, imports);
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.containsEntityInSignature(iri, imports);
    }

    @Override
    public boolean containsClassInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.containsClassInSignature(iri, imports);
    }

    @Override
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.containsObjectPropertyInSignature(iri, imports);
    }

    @Override
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.containsDataPropertyInSignature(iri, imports);
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.containsAnnotationPropertyInSignature(iri, imports);
    }

    @Override
    public boolean containsDatatypeInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.containsDatatypeInSignature(iri, imports);
    }

    @Override
    public boolean containsIndividualInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.containsIndividualInSignature(iri, imports);
    }

    @Override
    public boolean containsDatatypeInSignature(@Nonnull IRI iri) {
        return ont.containsDatatypeInSignature(iri);
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull IRI iri) {
        return ont.containsEntityInSignature(iri);
    }

    @Override
    public boolean containsClassInSignature(@Nonnull IRI iri) {
        return ont.containsClassInSignature(iri);
    }

    @Override
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri) {
        return ont.containsObjectPropertyInSignature(iri);
    }

    @Override
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri) {
        return ont.containsDataPropertyInSignature(iri);
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri) {
        return ont.containsAnnotationPropertyInSignature(iri);
    }

    @Override
    public boolean containsIndividualInSignature(@Nonnull IRI iri) {
        return ont.containsIndividualInSignature(iri);
    }

    @Nonnull
    @Override
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return ont.getEntitiesInSignature(iri, imports);
    }

    @Override
    public Set<IRI> getPunnedIRIs(@Nonnull Imports imports) {
        return ont.getPunnedIRIs(imports);
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        return ont.containsReference(owlEntity, imports);
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity owlEntity) {
        return ont.containsReference(owlEntity);
    }

    @Nonnull
    @Override
    public Set<OWLClass> getClassesInSignature(boolean b) {
        return ont.getClassesInSignature(b);
    }

    @Nonnull
    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean b) {
        return ont.getObjectPropertiesInSignature(b);
    }

    @Nonnull
    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean b) {
        return ont.getDataPropertiesInSignature(b);
    }

    @Nonnull
    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean b) {
        return ont.getIndividualsInSignature(b);
    }

    @Nonnull
    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(boolean b) {
        return ont.getReferencedAnonymousIndividuals(b);
    }

    @Nonnull
    @Override
    public Set<OWLDatatype> getDatatypesInSignature(boolean b) {
        return ont.getDatatypesInSignature(b);
    }

    @Nonnull
    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(boolean b) {
        return ont.getAnnotationPropertiesInSignature(b);
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity, boolean b) {
        return ont.containsEntityInSignature(owlEntity, b);
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull IRI iri, boolean b) {
        return ont.containsEntityInSignature(iri, b);
    }

    @Override
    public boolean containsClassInSignature(@Nonnull IRI iri, boolean b) {
        return ont.containsClassInSignature(iri, b);
    }

    @Override
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri, boolean b) {
        return ont.containsObjectPropertyInSignature(iri, b);
    }

    @Override
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri, boolean b) {
        return ont.containsDataPropertyInSignature(iri, b);
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri, boolean b) {
        return ont.containsAnnotationPropertyInSignature(iri, b);
    }

    @Override
    public boolean containsDatatypeInSignature(@Nonnull IRI iri, boolean b) {
        return ont.containsDatatypeInSignature(iri, b);
    }

    @Override
    public boolean containsIndividualInSignature(@Nonnull IRI iri, boolean b) {
        return ont.containsIndividualInSignature(iri, b);
    }

    @Nonnull
    @Override
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri, boolean b) {
        return ont.getEntitiesInSignature(iri, b);
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity owlEntity, boolean b) {
        return ont.containsReference(owlEntity, b);
    }

    @Nonnull
    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return ont.getDataPropertiesInSignature();
    }
}