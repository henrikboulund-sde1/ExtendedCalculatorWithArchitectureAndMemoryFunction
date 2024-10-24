package dk.easv.bll;

import dk.easv.dal.MemoryData;

import java.util.ArrayList;
import java.util.List;

public class MemoryLogic {

    private List<String> MemoryList = null;
    private MemoryData MemoryData = new MemoryData();

    public void addToMemory(String s)
    {
        MemoryList.add(s);
        MemoryData.writeToFile(s);
    }

    public List<String> loadMemory(boolean forceReload)
    {
        if(MemoryList == null || forceReload)
        {
            MemoryList = this.MemoryData.loadData();
            MemoryList = MemoryList.reversed();
        }
        return MemoryList;
    }

    public boolean clearMemory()
    {
        boolean result = MemoryData.clearFile();
        if(result) MemoryList.clear();
        return result;
    }
}
