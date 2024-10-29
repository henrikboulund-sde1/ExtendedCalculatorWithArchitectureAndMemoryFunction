package dk.easv.dal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemoryData {
    private File MemoryFile = new File("/Users/henrikboulund/CalcMemory.txt");

    public MemoryData()
    {
        if(!(MemoryFile.exists()))
        {
            try
            {
                MemoryFile.createNewFile();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> loadData()
    {
        List<String> result = new ArrayList<>();
        try
        {
            Scanner fileReader = new Scanner(MemoryFile);
            while(fileReader.hasNextLine())
            {
                result.add(fileReader.nextLine());
            }
            fileReader.close();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean writeToFile(String textToAppend)
    {
        try
        {
            FileWriter fw = new FileWriter(MemoryFile.getPath(), true);
            fw.write("\n");
            fw.write(textToAppend);
            fw.close();
            return true;
        }
        catch(IOException e)
        {
            return false;
        }
    }

    public boolean clearFile()
    {
        if(MemoryFile.delete())
        {
            try
            {
                MemoryFile.createNewFile();
                return true;
            }
            catch (IOException e)
            {
                return false;
            }
        }
        return false;
    }
}
