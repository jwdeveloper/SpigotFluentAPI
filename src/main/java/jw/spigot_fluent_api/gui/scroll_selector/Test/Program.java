package jw.spigot_fluent_api.gui.scroll_selector.Test;

import org.bukkit.Material;

public class Program
{
    public void run()
    {
        while (true)
        {

            USB usb = new USB();
            Kabel KabelUsb = (Kabel)usb;
            //Skaner
            String menuName = System.in.toString();
            int numerMenu = 2;
            var wybor =  Choice.values()[numerMenu];

            for(Material material : Material.values())
            {
                System.out.println(material.name());
            }

            var choices = Choice.values();
            for(int i=0;i<choices.length;i++)
            {
                System.out.println(i+" Wybierz"+ choices[i].name());
            }
            // 0 WYBIERZ ADDTIme
            //1 wybvierz editTime
            //2 wybierz END


            Choice dupa = Choice.valueOf(menuName);
            switch (dupa)
            {
                case SHOWTIME -> showTimeToUsew();
                case END -> endProgram();

            }
        }
    }

    public  void endProgram()
    {

    }


    public void showTimeToUsew()
    {

    }

}
