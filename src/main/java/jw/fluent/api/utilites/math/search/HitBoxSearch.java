package jw.fluent.api.utilites.math.search;

import jw.fluent.api.utilites.math.InteractiveHitBox;
import lombok.Data;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HitBoxSearch<T extends SearchableHitBox>
{

    private T[] hitboxes;


    public HitBoxSearch(T[] hitBoxes)
    {
         this.hitboxes = hitBoxes;
    }


    public void build()
    {

        var result = new LinkedList<Nodes>();

        for(var i =1;i<hitboxes.length;i+=2)
        {
            var left = hitboxes[i-1];
            var right = hitboxes[i];

            var node = new Nodes();

            if(left != null)
            {
                node.setLeft(left.getHitBox());
                node.setLeftData(left);
            }

            if(right != null)
            {
                node.setRightData(right);
                node.setRight(right.getHitBox());
            }


            var root = new InteractiveHitBox(left.getHitBox().getOrigin(),left.getHitBox().getMin(), right.getHitBox().getMax());
            node.setRoot(root);

            result.add(node);
        }


    }

    public void find(Player player)
    {

    }





    @Data
    public class Nodes
    {
        private InteractiveHitBox root;

        private InteractiveHitBox left;
        private T leftData;

        private InteractiveHitBox right;
        private T rightData;
    }
}
