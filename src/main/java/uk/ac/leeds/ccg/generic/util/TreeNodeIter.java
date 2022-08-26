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

import java.util.Iterator;

/**
 * Code adapted from https://github.com/gt4dev/yet-another-tree-structure.
 * 
 * @author Andy Turner
 */
public class TreeNodeIter<T> implements Iterator<TreeNode<T>> {

    enum ProcessStages {
        ProcessParent, ProcessChildCurNode, ProcessChildSubNode
    }

    private final TreeNode<T> treeNode;

    public TreeNodeIter(TreeNode<T> treeNode) {
        this.treeNode = treeNode;
        this.doNext = ProcessStages.ProcessParent;
        this.childrenCurNodeIter = treeNode.children.iterator();
    }

    private ProcessStages doNext;
    private TreeNode<T> next;
    private final Iterator<TreeNode<T>> childrenCurNodeIter;
    private Iterator<TreeNode<T>> childrenSubNodeIter;

    @Override
    public boolean hasNext() {
        if (this.doNext == ProcessStages.ProcessParent) {
            this.next = this.treeNode;
            this.doNext = ProcessStages.ProcessChildCurNode;
            return true;
        }
        if (this.doNext == ProcessStages.ProcessChildCurNode) {
            if (childrenCurNodeIter.hasNext()) {
                TreeNode<T> childDirect = childrenCurNodeIter.next();
                childrenSubNodeIter = childDirect.iterator();
                this.doNext = ProcessStages.ProcessChildSubNode;
                return hasNext();
            } else {
                this.doNext = null;
                return false;
            }
        }
        if (this.doNext == ProcessStages.ProcessChildSubNode) {
            if (childrenSubNodeIter.hasNext()) {
                this.next = childrenSubNodeIter.next();
                return true;
            } else {
                this.next = null;
                this.doNext = ProcessStages.ProcessChildCurNode;
                return hasNext();
            }
        }
        return false;
    }

    @Override
    public TreeNode<T> next() {
        return this.next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
