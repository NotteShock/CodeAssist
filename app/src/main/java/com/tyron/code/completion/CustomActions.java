package com.tyron.code.completion;
import com.tyron.code.model.CompletionItem;
import com.tyron.code.model.CompletionList;
import com.tyron.code.ParseTask;
import java.util.List;
import java.util.ArrayList;
import com.tyron.code.util.StringSearch;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.tree.JCTree;
import android.util.Log;
import com.sun.source.tree.Tree;
import com.sun.source.tree.AnnotationTree;

/**
 * Convenience class for getting completions on custom actions like
 * Overriding a method
 */
public class CustomActions {
	
	private static final String TAG = CustomActions.class.getSimpleName();
	
	public static List<CompletionItem> addCustomActions(ParseTask task, String partial) {
		List<CompletionItem> items = new ArrayList<>();

		return items;
	}

	public static void addOverrideItem(CompletionList list) {
			CompletionItem item = new CompletionItem();
			item.action = CompletionItem.Kind.OVERRIDE;
			item.label = "Override methods";
			item.commitText = "";
			item.detail = "";
			list.items.add(0, item);
	}
}