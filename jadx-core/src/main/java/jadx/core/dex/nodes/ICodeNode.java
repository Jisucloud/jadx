package jadx.core.dex.nodes;

import jadx.api.metadata.ICodeDefinition;
import jadx.core.dex.attributes.IAttributeNode;
import jadx.core.dex.info.AccessInfo;

public interface ICodeNode extends IDexNode, IAttributeNode, IUsageInfoNode, ICodeDefinition {
	AccessInfo getAccessFlags();

	void setAccessFlags(AccessInfo newAccessFlags);
}
