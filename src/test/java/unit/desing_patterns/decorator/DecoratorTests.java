package unit.desing_patterns.decorator;

import jw.spigot_fluent_api.desing_patterns.decorator.FluentDecorator;
import jw.spigot_fluent_api.desing_patterns.decorator.api.annoatations.Decorator;
import lombok.SneakyThrows;
import org.junit.Test;

public class DecoratorTests
{


    @SneakyThrows
    @Test
    public void Should_Decorate()
    {

        //FluentDecorator.decorate(IEntity.class, EntityDataAnalizer.class);
    }


    public interface IEntity
    {
        public String doWork(String siema);
    }

    public class Entity implements IEntity {

        @Override
        public String doWork(String siema) {
            return null;
        }
    }


    @Decorator(target = Entity.class)
    public class EntityDataAnalizer implements IEntity
    {
        private final IEntity _entity;

        public EntityDataAnalizer(IEntity entity)
        {
            _entity = entity;
        }

        @Override
        public String doWork(String siema) {
            return _entity.doWork(siema+" jacek");
        }
    }

    public class EntityCounter implements IEntity
    {
        private final IEntity _entity;
        private int count;

        public EntityCounter(IEntity entity)
        {
            _entity = entity;
        }

        @Override
        public String doWork(String siema) {
            count ++;
            if(count > 10)
            {
                return "No more player can play";
            }
            return _entity.doWork(siema);
        }
    }

}
