package jadx.gui.treemodel;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import jadx.api.ICodeInfo;
import jadx.api.JavaNode;
import jadx.gui.ui.TabbedPane;
import jadx.gui.ui.panel.ContentPanel;

public abstract class JNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5154479091781041008L;

	public abstract JClass getJParent();

	/**
	 * Return top level JClass or self if already at top.
	 */
	public JClass getRootClass() {
		return null;
	}

	public JavaNode getJavaNode() {
		return null;
	}

	@Nullable
	public ContentPanel getContentPanel(TabbedPane tabbedPane) {
		return null;
	}

	public String getSyntaxName() {
		return SyntaxConstants.SYNTAX_STYLE_NONE;
	}

	public int getLine() {
		return 0;
	}

	@NotNull
	public ICodeInfo getCodeInfo() {
		return ICodeInfo.EMPTY;
	}

	public final Integer getSourceLine(int line) {
		return getCodeInfo().getLineMapping().get(line);
	}

	public abstract Icon getIcon();

	public String getName() {
		JavaNode javaNode = getJavaNode();
		if (javaNode == null) {
			return null;
		}
		return javaNode.getName();
	}

	public boolean canRename() {
		return false;
	}

	public abstract String makeString();

	public String makeStringHtml() {
		return makeString();
	}

	public String makeDescString() {
		return null;
	}

	public boolean hasDescString() {
		return false;
	}

	public String makeLongString() {
		return makeString();
	}

	public String makeLongStringHtml() {
		return makeLongString();
	}

	public int getPos() {
		JavaNode javaNode = getJavaNode();
		if (javaNode == null) {
			return -1;
		}
		return javaNode.getDefPos();
	}

	public String getTooltip() {
		return null;
	}

	@Override
	public String toString() {
		return makeString();
	}
}
