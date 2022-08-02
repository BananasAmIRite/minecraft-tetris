package org.bananasamirite.minecrafttetris.blockdata;

import org.bananasamirite.minecrafttetris.exceptions.AnchorNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Stores all the groups in memory
 *
 * */
public class TetrisBlockGroupManager {
    private final List<TetrisBlockGroup> groups;


    public TetrisBlockGroupManager() {
        this(new ArrayList<>());
    }

    public TetrisBlockGroupManager(List<TetrisBlockGroup> groups) {
        this.groups = groups;
    }

    public void addGroup(TetrisBlockGroup group) {
        if (group == null) return;
        this.groups.add(group);
    }

    public TetrisBlockGroup loadFromFile(File f) throws FileNotFoundException, AnchorNotFoundException {
        if (!f.exists()) throw new FileNotFoundException();

        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append(System.lineSeparator());
        }
        scanner.close();

        TetrisBlockGroup g = TetrisBlockGroup.fromString(sb.toString());
        addGroup(g);
        return g;
    }


    public void loadAllFromFile(File f) throws FileNotFoundException, AnchorNotFoundException {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(f);
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append(System.lineSeparator());
        }
        scanner.close();

        String[] blockDatas = sb.toString().split(":");

        for (String data : blockDatas) {
            TetrisBlockGroup g = TetrisBlockGroup.fromString(data);
            addGroup(g);
        }
    }

    public void registerBlockDatas(List<String> datas) throws AnchorNotFoundException {
        for (String data : datas) {
            TetrisBlockGroup g = TetrisBlockGroup.fromString(data);
            if (g != null) addGroup(g);
        }
    }

    public TetrisBlockGroup randBlockGroup() {
        if (groups.size() == 0) return null;

        return groups.get((int) Math.floor(Math.random() * groups.size())); // TODO: TEST: implement this
    }

    public List<TetrisBlockGroup> getGroups() {
        return groups;
    }
}
