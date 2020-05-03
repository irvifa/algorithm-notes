package com.github.irvifa.algorithmnotes.leetcode.tree.binarytreeinordertraversal;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.irvifa.algorithmnotes.leetcode.tree.Helper;
import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import java.util.Arrays;
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
    String input = "1,null,2,3";
    TreeNode root = Helper.stringToTreeNode(input);
    List<Integer> result = underTest.inorderTraversal(root);
    assertThat(result, is(equalTo(Arrays.asList(1, 3, 2))));
  }
}
