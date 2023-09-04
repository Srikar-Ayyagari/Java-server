package main.util.file;

import java.io.*;
public class Files implements Serializable{
    private String name;
    private String path;
    private byte[] data;
    private int size;
    public int init;
    public Files(){
        init = 0;
    }
    public Files(String path, String name) throws IOException {
        init = 1;
        this.path = path;
        this.name = name;
        FileInputStream fileInputStream = new FileInputStream(path + File.separator + name);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        bufferedInputStream.close();
        fileInputStream.close();
        this.data = byteArrayOutputStream.toByteArray();
        this.size = this.data.length;
    }

    public void writeToFile(String destPath, String destFileName) throws IOException {
        FileOutputStream outputStream = null;
        try {
            // create the output stream to write the file contents to the destination file
            outputStream = new FileOutputStream(destPath +"/"+ destFileName);

            // write the file data to the output stream
            outputStream.write(data);
            
            // flush and close the output stream
            outputStream.flush();
            outputStream.close();
            
            System.out.println("File saved to " + destPath + "/" + destFileName);
        } catch (IOException e) {
            // handle any errors during file output
            System.out.println("Error saving file: " + e.getMessage());
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    System.out.println("Error closing output stream: " + ex.getMessage());
                }
            }
        }
    }
    public int getSize() {
        return size;
    }
    public byte[] getData() {
        return this.data;
    }
    public String getPath() {
        return path;
    }
    public String getName() {
        return name;
    }
    public byte[] toByteArray() {
        return null;
    }
}
