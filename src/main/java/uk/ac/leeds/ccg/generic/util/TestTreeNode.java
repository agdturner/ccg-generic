/*
 * Copyright 2022 Centre for Computational Geography, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.generic.util;

/**
 * Code adapted from https://github.com/gt4dev/yet-another-tree-structure.
 * 
 * @author Andy Turner
 */
public class TestTreeNode {

    /**
     * Create a new instance.
     */
    public TestTreeNode(){}
            
    public static TreeNode<String> getTest() {
        TreeNode<String> root = new TreeNode<>("root");
        root.addChild("node0");
        root.addChild("node1");
        TreeNode<String> node2 = root.addChild("node2");
        node2.addChild(null);
        TreeNode<String> node21 = node2.addChild("node21");
        node21.addChild("node210");
        node21.addChild("node211");
        TreeNode<String> node3 = root.addChild("node3");
        node3.addChild("node30");
        return root;
    }

    private static String createIndent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        TreeNode<String> tree = getTest();
        for (TreeNode<String> node : tree) {
            String indent = createIndent(node.getLevel());
            System.out.println(indent + node.data);
        }
    }

}
