# The-Quest-of-Legends

**Compile and Run**  
When pwd --> XXXX/The-Quest-of-Legends-T29
"javac Main.java"  
"java Main"  


**Class Design:**  

Interface:
 - DataInitialize
 - Encounter
 - Fight
 - Regeneration
 - BuyorSell

Cells
   - PlainCells (CommonCells)
   - InAccessibleCells (NonAccessibleCells)
   - NexusCells/Markets
   - BushCells
   - KoulouCells
   - CaveCells

Roles
   - Heroes
     - Warriors
     - Paladins
     - Sorcerers
   - Monsters
     - Dragons
     - Exoskeletons
     - Spirits

Items
   - Weapons
   - Armors
   - Spells
     - IceSpells
     - FileSpells
     - LigntningSpells
   - Potions

Map
   - TheQuestMap
   - TheQuestOfLegendsMap

Teams
   - TeamTheQuest
   - TeamTheQuestOfLegends

Simulation
   - TheQuestSimulation
   - TheQuestOfLegendsSimulation

Main  
Backpack  
Probability  
Rounds  
ZshColors  

-----------------------------------------------
When hero is not in a fight with monster, he can
 - Display his status
 - Check his inventory
 - W/A/S/D
 - Teleport
 - B (back to Nexus cell)
 - Equip/Change Weapon
 - Equip/Change Armor
 - Display the map
 - Quit the game  
Only action "W/A/S/D", "Teleport" and "B" will cost one action point.

-----------------------------------------------
Assume that hero won't fight with monster when they encounter at a Nexus cell  

-----------------------------------------------
Pipeline for doing business:  
 - Do you want to do business?  
   - If yes, tell me who want to do business  
     - What kind of business? Buy or Sell?  
       - Buy what / Sell what  
After you buy a item, go back to the beginning. So you can choose if you want to do business again.  


-----------------------------------------------
In order to win a fight, you should:  
 - BUY a/some spells for each hero  
 - for each hero, BUY a weapon and EQUIP it   
 - for each hero, BUY a armor and EQUIP it  
If you don't do that, you will lose in most cases.  
If you encounter monsters before you arrive at a market, you definitely lose. The reality is really crucial. Restart a new game/life, don't hesitate.  


-----------------------------------------------
A fight consists of several rounds
At each round, each hero has only one action point. He can do one following action once.
 - Attack
 - Cast a spell
 - Drink a potion
 - Equip/Change Weapon
 - Equip/Change Armor
 - Display the status of your team(heroes) and monsters (I don't think this action deserves one action point because I display their status after attacking, casting or drinking without consuming action points.)

-----------------------------------------------
I adjusted the attributes of weapons. 
 - Set the base damage of Shield and Dagger to 300 and 400  
 - Then, base damage *= 5  
I changed some heroes' name.  
Heroes can't know monsters' status value before encountering them.  

