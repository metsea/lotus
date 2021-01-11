package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.dao.entity.TriggerMatrixIndex;
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
public class TriggerMatrixIndexMapperTest {

    @Autowired
    private TriggerMatrixIndexMapper triggerMatrixIndexMapper;

    @Test
    public void TestInsert(){
        TriggerMatrixIndex triggerMatrixIndex = new TriggerMatrixIndex();
        triggerMatrixIndex.setJobId(1);
        triggerMatrixIndex.setName("A1");
        int insert = triggerMatrixIndexMapper.insert(triggerMatrixIndex);
        Assert.assertEquals(insert,1);
    }

    @Test
    public void testUpdate(){
        TriggerMatrixIndex  triggerMatrixIndex = new TriggerMatrixIndex();
        triggerMatrixIndex.setId(2);
        triggerMatrixIndex.setName("A2");
        int updateById = triggerMatrixIndexMapper.updateById(triggerMatrixIndex);
        Assert.assertEquals(updateById,1);
    }

    @Test
    public void testDelete(){
        int deleteById = triggerMatrixIndexMapper.deleteById(2);
        Assert.assertEquals(deleteById,1);
    }

    @Test
    public void testQuery(){
        TriggerMatrixIndex triggerMatrixIndex = triggerMatrixIndexMapper.selectById(3);
        Assert.assertNotNull(triggerMatrixIndex);
    }

    @Test
    public void testQueryList(){
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = triggerMatrixIndexMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage(){
        Page<TriggerMatrixIndex> page = new Page(1,2);
        QueryWrapper wrapper = new QueryWrapper<TriggerMatrixIndex>();
        wrapper.orderByDesc("id");
        Page page1 = triggerMatrixIndexMapper.selectPage(page, wrapper);
        List<TriggerMatrixIndex> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
