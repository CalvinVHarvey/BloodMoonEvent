# BloodMoonEvent
A Blood Moon plugin developed Originally Developed for the Frontier minecraft server, adds bloodmoons with custom mobs




#Introduction

  Adds Blood Moon events that are determined by the percent set in the config for what the percentage of one happening every night
  You are able to set the percentage in the config, as well as there are custom mobs that spawn, in which have some configurability in the Config
  You could edit things such as the min and max health.
  You are able to configure the drops for the mobs. Where when you kill a Blood moon mob it pulls a random item from the drops list.
  For the configuration of the loot read the looter.yml when you install the comments should give you clear instructions.






#Permissions and Commands

  Bloodmoon.give - /giverandomloot - gives you random item from the loot table for Blood Moon Loot Set

  Bloodmoon.start - /bloodmoon start - starts a Blood Moon if it is night time

  Bloodmoon.end - /bloodmoon end - ends a Blood Moon if there is one going on

  Bloodmoon.reload - /bloodmoon reload - reloads the yml file for the loot for the Blood Moon Mobs.
  
  
  

#Looter.yml Tutorial 

A more in depth explanation is on the wiki for how to edit the loot file for the mob drops. 

    exampleItem:   #This can be called anything its just the section for the Item
      type: DIRT #Minecraft item ID
      amount: 1 #The Amount of the item for the drop
      chanceForDrop: 60 #Percent for the drop 1-100 there can be decimals
      lore: 'its dirt' #Lore on the item
      enchantments:
        looting:    #This can be called anything its just a section to list 
          enchType: LOOTING #Enchantment ID for the enchantment
          level: 3  #Level of the enchant
        section2:
          enchType: SHARPNESS
          level: 5
        
 


