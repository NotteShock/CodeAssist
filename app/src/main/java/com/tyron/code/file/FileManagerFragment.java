package com.tyron.code.file;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.tyron.code.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.activity.OnBackPressedCallback;

import com.tyron.code.completion.CompletionEngine;
import com.tyron.code.main.MainFragment;
import com.tyron.code.model.Project;
import com.tyron.code.parser.FileManager;

@SuppressWarnings("FieldCanBeLocal")
public class FileManagerFragment extends Fragment {
    
    public static FileManagerFragment newInstance(File file) {
        FileManagerFragment fragment = new FileManagerFragment();
        Bundle args = new Bundle();
        args.putString("path", file.getAbsolutePath());
        fragment.setArguments(args);
        return fragment;
    }
    
    OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            if (!mCurrentFile.equals(mRootFile)) {
                mAdapter.submitFile(Objects.requireNonNull(mCurrentFile.getParentFile()));
                check(mCurrentFile.getParentFile());
            }
        }
    };

    private File mRootFile;
    private File mCurrentFile;
    
    private LinearLayout mRoot;
    private RecyclerView mListView;
    private LinearLayoutManager mLayoutManager;
    private FileManagerAdapter mAdapter;
    
    public FileManagerFragment() {
        
    }

    public void disableBackListener() {
        callback.setEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        mRootFile = new File(getArguments().getString("path"));
        if (savedInstanceState != null) {
            mCurrentFile = new File(savedInstanceState.getString("currentFile"), mRootFile.getAbsolutePath());
        } else {
            mCurrentFile = mRootFile;
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = (LinearLayout) inflater.inflate(R.layout.file_manager_fragment, container, false);

        mLayoutManager = new LinearLayoutManager(requireContext());
        mAdapter = new FileManagerAdapter();
        
        mListView = mRoot.findViewById(R.id.listView);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(mAdapter);
        
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        mAdapter.submitFile(mRootFile);
        mAdapter.setOnItemClickListener((file, position) -> {
            if (position == 0) {
                if (!mCurrentFile.equals(mRootFile)) {
                    mAdapter.submitFile(Objects.requireNonNull(mCurrentFile.getParentFile()));
                    check(mCurrentFile.getParentFile());
                }
                return;
            }

            if (file.isFile()) {
                openFile(file);
            } else if (file.isDirectory()) {
                mAdapter.submitFile(file);
                check(file);
            }
        });
    }
	
	private void openFile(File file) {
		Fragment parent = getParentFragment();
		
		if (parent != null) {
			if (parent instanceof MainFragment) {
				((MainFragment) parent).openFile(file);
			}
		}
	}
    /**
     * Checks if the current file is equal to the root file if so,
     * it disables the OnBackPressedCallback
     */
    private void check(File currentFile) {
        mCurrentFile = currentFile;

        callback.setEnabled(!currentFile.getAbsolutePath().equals(mRootFile.getAbsolutePath()));
    }
}