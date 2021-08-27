package com.tyron.code.editor.language;

import com.tyron.code.editor.language.java.JAVA;
import com.tyron.code.editor.language.xml.XML;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import io.github.rosemoe.editor.interfaces.EditorLanguage;
import io.github.rosemoe.editor.widget.CodeEditor;

public class LanguageManager {
	
	private static LanguageManager Instance = null;
	
	public static LanguageManager getInstance() {
		if (Instance == null) {
			Instance = new LanguageManager();
		}
		return Instance;
	}
	
	private final Set<Language> mLanguages = new HashSet<>();
	
	private LanguageManager() {
		initLanguages();
	}
	
	private void initLanguages() {
		mLanguages.addAll(
			Set.of(
				new XML(),
				new JAVA()
			)
		);
	}
	
	public EditorLanguage get(CodeEditor editor, File file) {
		for (Language lang : mLanguages) {
			if (lang.isApplicable(file)) {
				return lang.get(editor);
			}
		}
		return null;
	}
}