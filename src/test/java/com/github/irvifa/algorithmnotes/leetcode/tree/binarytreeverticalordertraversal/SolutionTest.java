package com.github.irvifa.algorithmnotes.leetcode.tree.binarytreeverticalordertraversal;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import com.github.irvifa.algorithmnotes.leetcode.tree.TreeNode;
import com.github.irvifa.algorithmnotes.leetcode.tree.TreeUtil;
import java.util.ArrayList;
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
  public void performVerticalOrderTraversal() throws Exception {
    String input = "3,9,20,null,null,15,7";
    TreeNode root = TreeUtil.stringToTreeNode(input);
    List<List<Integer>> result = underTest.verticalOrder(root);
    List<List<Integer>> expected = new ArrayList<>();
    expected.add(Arrays.asList(9));
    expected.add(Arrays.asList(3, 15));
    expected.add(Arrays.asList(20));
    expected.add(Arrays.asList(7));
    assertThat(result, is(equalTo(expected)));
  }
}
