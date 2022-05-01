package jadx.gui.jobs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jadx.api.ICodeWriter;
import jadx.api.JavaClass;
import jadx.gui.utils.CacheObject;
import jadx.gui.utils.search.StringRef;
import jadx.gui.utils.search.TextSearchIndex;

public class IndexService {

	private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);

	private final CacheObject cache;
	private boolean indexComplete;
	private final Set<JavaClass> indexSet = new HashSet<>();

	public IndexService(CacheObject cache) {
		this.cache = cache;
	}

	/**
	 * Warning! Not ready for parallel execution. Use only in a single thread.
	 */
	public boolean indexCls(JavaClass cls) {
		try {
			TextSearchIndex index = cache.getTextIndex();
			if (index == null) {
				return false;
			}
			index.indexNames(cls);
			indexSet.add(cls);
			return true;
		} catch (Exception e) {
			LOG.error("Index error in class: {}", cls.getFullName(), e);
			return false;
		}
	}

	public void indexResources() {
		TextSearchIndex index = cache.getTextIndex();
		index.indexResource();
	}

	public synchronized void refreshIndex(JavaClass cls) {
		TextSearchIndex index = cache.getTextIndex();
		if (index == null) {
			return;
		}
		indexSet.remove(cls);
		index.remove(cls);
		cls.decompile();
		indexCls(cls);
	}

	public synchronized void remove(JavaClass cls) {
		TextSearchIndex index = cache.getTextIndex();
		if (index == null) {
			return;
		}
		indexSet.remove(cls);
		index.remove(cls);
	}

	public boolean isIndexNeeded(JavaClass cls) {
		return !indexSet.contains(cls);
	}

	@NotNull
	protected static List<StringRef> splitLines(String code) {
		List<StringRef> lines = StringRef.split(code, ICodeWriter.NL);
		int size = lines.size();
		for (int i = 0; i < size; i++) {
			lines.set(i, lines.get(i).trim());
		}
		return lines;
	}

	public boolean isComplete() {
		return indexComplete;
	}

	public void setComplete(boolean indexComplete) {
		this.indexComplete = indexComplete;
	}
}
