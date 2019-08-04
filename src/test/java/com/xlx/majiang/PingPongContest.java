package com.xlx.majiang;

/**
 * 牛客网编程题
 *
 * @author xielx on 2019/7/5
 */
public class PingPongContest {


  public static void main(String[] args) {
    String[] team1 = {"a","b","c"};
    String[] team2 = {"x","y","z"};
    int k = -1;
    for (int i = 0; i < team1.length ; i++) {
      for (int j = 0; j < team2.length ; j++) {
        if (i == 0 && j == 0){//a说不和x比
          continue;
        }else if (i == 2 && (j == 0 || j == 2)){//c说不和x,z比
          continue;
        }else {
            if (i != k){
              System.out.println(team1[i] + "----------" + team2[j]);
            k = i;
            }
        }
      }
    }
  }
}
