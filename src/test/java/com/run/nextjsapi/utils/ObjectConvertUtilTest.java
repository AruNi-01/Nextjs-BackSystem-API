package com.run.nextjsapi.utils;

import com.run.nextjsapi.entity.User;
import com.run.nextjsapi.entity.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/11/18
 */
@SpringBootTest
public class ObjectConvertUtilTest {

    @Test
    public void testConvertObject() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("AruNi_Lu");
        userDTO.setTeam("team1");
        User user = ObjectConvertUtil.convertObject(userDTO, User.class, (source, target) -> {
            if (source.getTeam() != null) {
                target.setTeamId(1);
            }
        });
        System.out.println(user);
        // User(id=null, name=AruNi_Lu, age=null, gender=null, phone=null, teamId=1, createTime=null)

        List<UserDTO> userDTOList = new ArrayList<>(List.of(userDTO));
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setName("AruNi_Lu2");
        userDTOList.add(userDTO2);
        List<User> userList = ObjectConvertUtil.convertList(userDTOList, User.class, (source, target) -> {
            if (source.getTeam() != null) {
                target.setTeamId(1);
            }
        });
        System.out.println(userList);
        /*
        [User(id=null, name=AruNi_Lu, age=null, gender=null, phone=null, teamId=1, createTime=null),
        User(id=null, name=AruNi_Lu2, age=null, gender=null, phone=null, teamId=null, createTime=null)]
         */
    }

}
