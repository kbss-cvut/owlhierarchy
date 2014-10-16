package cz.cvut.kbss.taxcat.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import org.protege.editor.core.ui.view.ViewComponent;
import org.protege.editor.core.ui.view.ViewComponentPlugin;
import org.protege.editor.core.ui.view.ViewComponentPluginAdapter;
import org.protege.editor.core.ui.workspace.Workspace;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.ui.OWLIcons;
import org.protege.editor.owl.ui.action.AbstractOWLTreeAction;
import org.protege.editor.owl.ui.renderer.OWLSystemColors;
import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;
import org.protege.editor.owl.ui.tree.OWLObjectTree;
import org.protege.editor.owl.ui.view.AbstractActiveOntologyViewComponent;
import org.protege.editor.owl.ui.view.cls.ToldOWLClassHierarchyViewComponent;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;

/**
 * @author Petr Křemen
 */
public class TaxonomyCategorizationView extends
		AbstractActiveOntologyViewComponent {
	private static final long serialVersionUID = 1L;
//	private JButton button;
	private ToldOWLClassHierarchyViewComponent viewx;

	protected void initialiseOntologyView() throws Exception {
//		button = new JButton("reload");
//		button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				initComponents();
//
//			}
//		});
		initComponents();
	}

	protected void disposeOntologyView() {
	}

	private ViewComponentPlugin get() {
		return new ViewComponentPluginAdapter() {
			public String getLabel() {
				return "Taxonomy Categorization";
			}

			public Workspace getWorkspace() {
				return getOWLEditorKit().getWorkspace();
			}

			public ViewComponent newInstance() throws ClassNotFoundException,
					IllegalAccessException, InstantiationException {
				viewx = new ToldOWLClassHierarchyViewComponent() {
					@Override
					public void performExtraInitialisation() throws Exception {
						super.performExtraInitialisation();
						addAction(getAnnotationAction("Location","class.add.sub.png", getTree())
								, "L", "L");
						addAction(getAnnotationAction("Activity","class.add.sub.png", getTree())
								, "L", "L");
						addAction(getAnnotationAction("Problem","class.add.sub.png", getTree())
								, "L", "L");
					}
				};
				viewx.setup(this);
				return viewx;
			}

			public Color getBackgroundColor() {
				return OWLSystemColors.getOWLClassColor();
			}

		};
	}

	private AbstractOWLTreeAction getAnnotationAction( final String name, final String icon, OWLObjectTree<OWLClass> tree) {
		return new AbstractOWLTreeAction<OWLClass>(name,
				OWLIcons.getIcon(icon),
				tree.getSelectionModel()) {
			private static final long serialVersionUID = -4067967212391062364L;

			public void actionPerformed(
					ActionEvent event) {
				final OWLModelManager mm = getOWLWorkspace().getOWLModelManager();
				final List<OWLOntologyChange> changes = new ArrayList<>();
				final OWLClass clz = getOWLDataFactory().getOWLClass(IRI.create("http://annotati.on/"+name));
				changes.add(new AddAxiom(mm.getActiveOntology(),mm.getOWLDataFactory().getOWLClassAssertionAxiom(clz, mm.getOWLDataFactory().getOWLNamedIndividual(getSelectedOWLEntity().asOWLClass().getIRI()))));				
				mm.applyChanges(changes);				
			}

			protected boolean canPerform(OWLClass cls) {
				return true; // TODO in settings -
								// selected set of
								// categories
			}
		};
	}
	
	private void initComponents() {
		final JPanel pnlMain = new JPanel(new BorderLayout());

		OWLClassSelectorPanel view = new OWLClassSelectorPanel(
				getOWLEditorKit()) {
			protected ViewComponentPlugin getViewComponentPlugin() {
				return get();
			}

			public void setSelection(OWLClass cls) {
				viewx.setSelectedEntity(cls);
			}

			public void setSelection(Set<OWLClass> clses) {
				viewx.setSelectedEntities(clses);
			}

			public OWLClass getSelectedObject() {
				return viewx.getSelectedEntity();
			}

			public Set<OWLClass> getSelectedObjects() {
				return viewx.getSelectedEntities();
			}

			public void dispose() {
				viewx.dispose();
			}

			public void addSelectionListener(ChangeListener listener) {
				viewx.addChangeListener(listener);
			}

			public void removeSelectionListener(ChangeListener listener) {
				viewx.removeChangeListener(listener);
			}
		};

		view.setBorder(BorderFactory.createEtchedBorder());
		pnlMain.add(view, BorderLayout.CENTER);
//		pnlMain.add(button, BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.add(pnlMain, BorderLayout.CENTER);
	}

	@Override
	protected void updateView(OWLOntology activeOntology) throws Exception {
		// TODO Auto-generated method stub
	}
}
