package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.common.enums.JobStatusEnum;
import cn.metsea.lotus.common.enums.JobTypeEnum;
import cn.metsea.lotus.dao.entity.Job;
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
 * Job Mapper Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@Rollback()
public class JobMapperTest {

    @Autowired
    private JobMapper jobMapper;

    @Test
    public void TestInsert() {
        Job job = new Job();
        job.setName("jobName4");
        job.setDescription("Test add one job");
        job.setType(JobTypeEnum.HIVE);
        int insert = jobMapper.insert(job);
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        Job job = new Job();
        job.setId(9);
        job.setStatus(JobStatusEnum.ONLINE);
        int updateById = jobMapper.updateById(job);
        Assert.assertEquals(updateById, 1);
    }

    @Test
    public void testDelete() {
        int deleteById = jobMapper.deleteById(9);
        Assert.assertEquals(deleteById, 1);
    }

    @Test
    public void testQuery() {
        Job job = jobMapper.selectById(10);
        Assert.assertNotNull(job);
    }

    @Test
    public void testQueryList() {
        Set set = new TreeSet<Integer>();
        set.add(10);
        set.add(12);
        List list = jobMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage() {
        Page<Job> page = new Page(1, 2);
        QueryWrapper wrapper = new QueryWrapper<Job>();
        wrapper.orderByDesc("id");
        Page page1 = jobMapper.selectPage(page, wrapper);
        List<Job> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
