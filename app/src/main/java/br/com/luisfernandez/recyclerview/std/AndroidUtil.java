package br.com.luisfernandez.recyclerview.std;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by luisfernandez on 24/05/18.
 */

public class AndroidUtil {

    public static String getJsonFromRaw(@NonNull Context context, @RawRes int resRawId) throws IOException
    {
        InputStream inputStream = context.getResources().openRawResource(resRawId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            inputStream.close();
        }

        return writer.toString();
    }
}
