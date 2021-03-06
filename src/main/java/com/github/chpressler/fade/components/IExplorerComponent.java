package com.github.chpressler.fade.components;

import com.github.chpressler.fade.IFile;

import java.util.ArrayList;

public interface IExplorerComponent {
	
	IFile getRoot();
	
	void setRoot(IFile f);
	
	IFile getCurrentDirectory();
	
	void setCurrentDirectory(IFile f);
	
	IFile[] getSelectedFiles();
	
	IFile getLastSelected();

    void addExplorerComponentListener(ExplorerComponentListener l);
	
	void removeExplorerComponentListener(ExplorerComponentListener l);
	
	ArrayList<ExplorerComponentListener> getExplorerComponentListeners();
	
	void selectionChanged(IFile lastSelected);

}
