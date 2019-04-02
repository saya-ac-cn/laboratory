package ac.cn.saya.laboratory;


import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Title: MapTestUtil
 * @ProjectName laboratory
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-03-24 20:14
 * @Description:
 */

public class MapTestUtil {

    @Test
    public void linkhash()
    {
        LinkedHashMap<String, String> linkedHashMap =
                new LinkedHashMap<String, String>();
        linkedHashMap.put("111", "111");
        linkedHashMap.put("222", "222");
        linkedHashMap.get("222");
        List list1 = new ArrayList<>();
        list1.add("1");
        List list2 = new LinkedList();
        list2.add("1");
    }

}
