package com.github.chpressler.fade;

public interface IRenameCommand {

    boolean rename(IFile file, String newName) throws Exception;

}
