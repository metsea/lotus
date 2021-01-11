package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.dao.entity.TriggerMatrix;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Schedule Instance Mapper Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@Rollback()
public class TriggerMatrixMapperTest {

    @Autowired
    private TriggerMatrixMapper triggerMatrixMapper;

    @Test
    public void TestInsert(){
        TriggerMatrix triggerMatrix = new TriggerMatrix();
        triggerMatrix.setTriggerId(10);
        triggerMatrix.setRule("{}");
        int insert = triggerMatrixMapper.insert(triggerMatrix);
        Assert.assertEquals(insert,1);
    }

    @Test
    public void testUpdate(){
        TriggerMatrix  triggerMatrix = new TriggerMatrix();
        triggerMatrix.setId(2);
        triggerMatrix.setRule("{}");
        int updateById = triggerMatrixMapper.updateById(triggerMatrix);
        Assert.assertEquals(updateById,1);
    }

    @Test
    public void testDelete(){
        int deleteById = triggerMatrixMapper.deleteById(2);
        Assert.assertEquals(deleteById,1);
    }

    @Test
    public void testQuery(){
        TriggerMatrix triggerMatrix = triggerMatrixMapper.selectById(3);
        Assert.assertNotNull(triggerMatrix);
    }

    @Test
    public void testQueryList(){
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = triggerMatrixMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage(){
        Page<TriggerMatrix> page = new Page(1,2);
        QueryWrapper wrapper = new QueryWrapper<TriggerMatrix>();
        wrapper.orderByDesc("id");
        Page page1 = triggerMatrixMapper.selectPage(page, wrapper);
        List<TriggerMatrix> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
