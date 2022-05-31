package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pojo.user;

import java.util.List;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

public interface usermapper {
    void insert(@Param("ip") String ip, @Param("msg") String msg);

    List<pojo.user>selectAll();



}
