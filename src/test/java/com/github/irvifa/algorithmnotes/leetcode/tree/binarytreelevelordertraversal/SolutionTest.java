package com.github.irvifa.algorithmnotes.leetcode.tree.binarytreelevelordertraversal;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.irvifa.algorithmnotes.leetcode.tree.Helper;
import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SolutionTest {

  private Solution underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new Solution();
  }

  @Test
  public void getInOrderTraversal() throws Exception {
    String input = "3,9,20,null,null,15,7";
    TreeNode root = Helper.stringToTreeNode(input);
    List<List<Integer>> result = underTest.levelOrder(root);
    assertThat(result, is(
        equalTo(
          ImmutableList.of(
            ImmutableList.of(3),
            ImmutableList.of(9, 20),
            ImmutableList.of(15, 7)
          )
        )
    ));
  }
}
