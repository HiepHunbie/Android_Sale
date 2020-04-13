package com.mcredit.mobile.mobile_for_sale.Utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by hiephunbie on 3/23/18.
 */

public class Compress{
        private static final int BUFFER = 204800;

        private ArrayList<String> _files;
        private String _zipFile;

        public Compress(ArrayList<String> files, String zipFile) {
            _files = files;
            _zipFile = zipFile;
//            //Check when unzip
//            _dirChecker("");
        }
    public void zip() {
            try  {
                BufferedInputStream origin = null;
                FileOutputStream dest = new FileOutputStream(_zipFile);

                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

                byte data[] = new byte[BUFFER];

                for(int i=0; i < _files.size(); i++) {
                    Log.v("Compress", "Adding: " + _files.get(i));
                    FileInputStream fi = new FileInputStream(_files.get(i));
                    origin = new BufferedInputStream(fi, BUFFER);
                    ZipEntry entry = new ZipEntry(_files.get(i).substring(_files.get(i).lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }
                    origin.close();
                }

                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }

        }
    public void unzip() {
        try  {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_zipFile + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);
        }

    }

    private void _dirChecker(String dir) {
        File f = new File(_zipFile + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }
}