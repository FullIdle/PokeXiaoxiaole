package me.fullidle.pokexiaoxiaole.pokexiaoxiaole.cmds;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class ACmd implements TabExecutor {
    private final ACmd superCmd;
    private final Map<String,ACmd> subCmdMap = new HashMap<>();
    private final String name;

    public ACmd(ACmd superCmd, String name){
        this.superCmd = superCmd;
        this.name = name;
        if (this.superCmd != null) {
            this.superCmd.subCmdMap.put(this.name,this);
        }
    }

    public String[] rOArgs(String[] args){
        ArrayList<String> list = Lists.newArrayList(args);
        if (list.isEmpty()) {
            return new String[0];
        }
        list.remove(0);
        if (list.isEmpty()) {
            return new String[0];
        }
        if (list.get(0).isEmpty()) {
            list.remove(0);
        }
        return list.toArray(new String[0]);
    }
}
