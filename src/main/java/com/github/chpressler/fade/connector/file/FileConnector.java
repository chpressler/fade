package com.github.chpressler.fade.connector.file;

import com.github.chpressler.fade.*;
import com.github.chpressler.fade.components.DriveSelectComponent;

import java.io.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileConnector implements IConnector {

    @Override
    public IFile getFile(URI uri) {
        return new com.github.chpressler.fade.connector.file.File(new File(uri), this);
    }

    @Override
    public IFile getFile(String path) {
        return new com.github.chpressler.fade.connector.file.File(new File(path), this);
    }

    @Override
    public File readFile(IFile f) throws IOException {
        return f.getFile();
    }

    @Override
    public List<IFile> getRootFiles() {
        FaDE.OSType os = FaDE.getOSType();
        switch (os) {
            case UNIX:
                Process mountProcess = null;
                try {
                    mountProcess = Runtime.getRuntime().exec("mount");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try(BufferedReader mountOutput = new BufferedReader(new InputStreamReader(mountProcess.getInputStream()))) {
                    List<File> roots = new ArrayList<File>();
                    while (true) {
                        String line = mountOutput.readLine();
                        if (line == null) {
                            break;
                        }
                        String[] sa = line.split(" ");
                        File f = new File(sa[2]);
                        if(f.exists()) {
                            roots.add(f);
                        }
                    }
                    mountOutput.close();
                    return convertFiles(roots);
                } catch (Exception e) {
                    Logger.getLogger(DriveSelectComponent.class.getName()).log(Level.SEVERE, e.getMessage());
                    List<File> roots = Arrays.asList(File.listRoots());
                    return convertFiles(roots);
                }
            case MS:
                // fall through
            default:
                List<File> roots = Arrays.asList(File.listRoots());
                return convertFiles(roots);
        }
    }

    @Override
    public IRenameCommand getRenameCommand() {
        return new FileRenameCommand();
    }

    @Override
    public ICopyCommand getCopyCommand() {
        return new FIleCopyCommand();
    }

    @Override
    public IDeleteCommand getDeleteCommand() {
        return new FileDeleteCommand();
    }

    @Override
    public IMoveCommand getMoveCommand() {
        return new FileMoveCommand();
    }

    @Override
    public ICreateCommand getCreateCommand() {
        return new FileCreateCommand();
    }

    private List<IFile> convertFiles(List<File> files) {
        List<IFile> ifiles = new ArrayList<IFile>();
        for(File f : files) {
            ifiles.add(new com.github.chpressler.fade.connector.file.File(f, this));
        }
        return ifiles;
    }

}
