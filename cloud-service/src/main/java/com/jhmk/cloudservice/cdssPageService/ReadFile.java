package com.jhmk.cloudservice.cdssPageService;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
public class ReadFile {

    public static void main(String[] args) {
        List<DrugNode> fileData = getFileData();
        List<DrugNode> tree = creatChildMedicineTree();
        Set<String> sets = getChildNameList("抗微生物药物", tree);

//        System.out.println(JSONObject.toJSONString(getChildNameList("骨骼肌松弛药", tree)));
        System.out.println(JSONObject.toJSONString(getAllChildNameList("骨骼肌松弛药", tree)));
        System.out.println(JSONObject.toJSONString(getAllChildNameList("麻醉辅助用药", tree)));



    }

    public List<DrugNode> getMedTree() {
        List<DrugNode> drugNodes = creatChildMedicineTree();
        return drugNodes;
    }

    @SuppressWarnings("MapOrSetKeyShouldOverrideHashCodeEquals")
    private static Set<DrugNode> buildTree(List<DrugNode> list, String s) {
        Set<DrugNode> menus = new HashSet<>();

        for (DrugNode menu : list) {

            String name = menu.getName();
            String pname = menu.getpName();
            if (pname.equals(s)) {
                Set<DrugNode> menuLists = buildTree(list, name);
                menu.setChildTrees(menuLists);
                menus.add(menu);
            }
            if (menus.size() == 0) {
                return null;
            }
        }

        return menus;
    }

    public static List<DrugNode> creatChildMedicineTree() {
        List<DrugNode> drugNodeList = getFileData();

        List<DrugNode> rootNodeList = new ArrayList<DrugNode>();
        for (DrugNode DrugNode : drugNodeList) {
            if (checkisroot(DrugNode, drugNodeList)) {
                rootNodeList.add(DrugNode);
            }
        }
        for (DrugNode DrugNode : rootNodeList) {
            DrugNode.setChildTrees(getChild(DrugNode.getName(), drugNodeList));
        }
        return rootNodeList;
    }

    public static boolean checkisroot(DrugNode DrugNode, List<DrugNode> drugNodeList) {
        for (DrugNode drugNode2 : drugNodeList) {
            if (drugNode2.getpName() != null && !drugNode2.getpName().isEmpty() && drugNode2.getName().equals(DrugNode.getName())) {
                return false;
            }
        }
        return true;
    }


    public static Set<DrugNode> getChild(String nodeName, List<DrugNode> drugNodeList) {
        // 子菜单
        Set<DrugNode> childList = new HashSet<>();
        for (DrugNode DrugNode : drugNodeList) {
            // 遍历所有节
            if (DrugNode.getpName() != null && !DrugNode.getpName().isEmpty()) {
                if (DrugNode.getpName().equals(nodeName)) {
                    childList.add(DrugNode);
                }
            }
        }

        for (DrugNode DrugNode : childList) {
            // 递归
            DrugNode.setChildTrees(getChild(DrugNode.getName(), drugNodeList));
        }
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    public static Set<DrugNode> getChildList(String nodeName, Set<DrugNode> drugNodeList) {
        Set<DrugNode> childList = new HashSet<>();
        for (DrugNode DrugNode : drugNodeList) {
            if (DrugNode.getName().equals(nodeName)) {
                childList.addAll(DrugNode.getChildTrees());
            } else {
                if (DrugNode.getChildTrees() != null) {
                    childList.addAll(getChildList(nodeName, DrugNode.getChildTrees()));
                }
            }
        }
        return childList;
    }

    //获取树节点
    public static List<DrugNode> getFileData() {
        String fileName = "drugLevel.txt";
        ClassPathResource resource = new ClassPathResource(fileName);
        List<DrugNode> nodeList = new ArrayList<DrugNode>();

        try {
            File medicineFile = resource.getFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(medicineFile)));
            String line = null;
            Set<String> bzset = new HashSet<>();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                for (int i = 0; i < split.length; i++) {
                    DrugNode drugNode = new DrugNode();
                    drugNode.setName(split[i]);
                    if (i == 0) {
                        drugNode.setpName("");
                    } else {
                        drugNode.setpName(split[i - 1]);
                    }
                    if (!bzset.contains(split[i])) {
                        bzset.add(split[i]);
                        nodeList.add(drugNode);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    /**
     * 获取下一子级名集合
     * @param nodeName
     * @param MedicineNodeList
     * @return
     */
    public static Set<String> getChildNameList(String nodeName, List<DrugNode> MedicineNodeList) {
        Set set = new HashSet(MedicineNodeList);
        Set<DrugNode> getChildList = getChildList(nodeName, set);
        Set<String> nodeNameList = new HashSet<String>();
        for (DrugNode node : getChildList) {
            nodeNameList.add(node.getName());

        }
        return nodeNameList;
    }

    /**
     * 获取所有下级节点名
     * @param nodeName
     * @param MedicineNodeList
     * @return
     */
    public static Set<String> getAllChildNameList(String nodeName, List<DrugNode> MedicineNodeList) {
        Set set = new HashSet(MedicineNodeList);
        Set<DrugNode> getChildList = getChildList(nodeName, set);
        Set<String> nodeNameList = new HashSet<String>();
        for (DrugNode node : getChildList) {
            nodeNameList = getClilds(node, nodeNameList);

        }
        return nodeNameList;
    }

    /**
     * 获取此节点名的下一集
     * @param nodeName
     * @param MedicineNodeList
     * @return
     */
    public static Set<String> getChildNames(String nodeName, List<DrugNode> MedicineNodeList) {
        Set set = new HashSet(MedicineNodeList);
        Set<DrugNode> getChildList = getChildList(nodeName, set);
        Set<String> nodeNameList = new HashSet<String>();
        for (DrugNode node : getChildList) {
            nodeNameList = getClilds(node, nodeNameList);

        }
        return nodeNameList;
    }

    public static Set<String> getClilds(DrugNode node, Set<String> list) {
        if (node!=null&&node.getChildTrees()!=null&&node.getChildTrees().size() > 0) {

            Set<DrugNode> childTrees = node.getChildTrees();
            for (DrugNode drugNode : childTrees) {
                list.add(drugNode.getName());
                getClilds(drugNode, list);
            }
        }
        return list;
    }
}
