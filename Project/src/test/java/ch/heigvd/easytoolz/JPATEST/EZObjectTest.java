package ch.heigvd.easytoolz.JPATEST;

import ch.heigvd.easytoolz.models.EZObject;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.util.Assert;

public class EZObjectTest extends EZToolzTest{

    @Test
    public void dataBaseShouldNotBeEmpty()
    {
        EZObject obj = new EZObject("name","description",null,null);
        obj.setOwner(userRepository.userNameIs("user1"));
        ezObjectRepository.save(obj);

        Pageable pageable = PageRequest.of(0,10);
        Assert.isTrue(ezObjectRepository.getAllByIsActive(true,pageable).getSize() > 0,"");
    }

    @Test
    @Commit
    public void databaseShouldHaveCorrectElements()
    {

        for(int i  = 0; i < 10; i++)
        {
            EZObject obj = new EZObject("name"+i,"description"+1,null,null);
            //String userName, String firstName, String lastName, String password, String email, Address address, boolean isAdmin
            obj.setOwner(userRepository.userNameIs("user1"));
            ezObjectRepository.save(obj);
        }
        Pageable pageable = PageRequest.of(0,60);
        Assert.isTrue(ezObjectRepository.getAllByIsActive(true,pageable).getContent().size() == 10,"");
    }

    @Test
    public void ShouldInsertCorrectly()
    {
        EZObject obj = new EZObject("test insert name","test insert description",null,null);
        obj.setOwner(userRepository.userNameIs("user1"));
        ezObjectRepository.save(obj);

        Assert.isTrue(ezObjectRepository.findByNameContaining("insert").size() > 0,"");
    }
}
