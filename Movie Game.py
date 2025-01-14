import time as t
import sys as s
import pyfiglet as p
import random as r


# Starts the game.
def start():

    # Text 1.
    text1 = ("\nWelcome to Choose or Die.\n" +
         "A game in which you will be playing with lives including your own.\n" +
         "In each stage of the game, you must choose or you will die.\n" +
         "If you successfully finish all stages, you will win an A in the Python class.\n" +
         "Are you ready to play?")
    
    # for loop to type text 1.
    for char in text1:
        t.sleep(0.05) # Delay the print of each char.
        s.stdout.write(char) # Print each character.
        s.stdout.flush() # Displays Immidiatly.
    s.stdout.write('\n') # Go to the next line.
    
    # while loop to keep asking the player for input until it's a yes or no.
    while True:
        user_start = input("> ").lower()

        # Player says yes and starts the game.
        if user_start == "yes":
            stage_1()
            break
        
        # Player says no and closes the game.
        elif user_start == "no":
            text2 =("You are not worthy of an A in the Python course. Be brave next time.")

            # For loop to type text 2.
            for char in text2:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

            break
        
        # Keep asking for input.
        else:
            text3 =("That is not a valid answer. Decide.")

            # For loop to type text 3.
            for char in text3:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

# Stage 1.
def stage_1():
    
    #Forest Scenario
    forest = ("\nWELCOME TO THE FIRST STAGE.\n" +
        "You and your 3 friends (Kelyan, Himanshu, and Johnny) are in the middle of a misty forest.\n" + 
        "None of you know where you're at but you can all feel the danger surrounding you.\n" +
        "Suddenly, you hear a strange noise behind you.\n" +
        "Johnny screams: There's a demon following us!!\n" +
        "You look back and see a large figure with rotten flesh and worms all over it... it's chasing after you.\n" +
        "Time to run.\n" +
        "Choose or Die: Left or Right?")
    
    # Dessert Scenario
    dessert = ("\nWELCOME TO THE FIRST STAGE.\n" +
               "You and your 3 friends (Kelyan, Himanshu, and Johnny) are in the middle of a dessert.\n" +
               "It's a very sunny day. You can feel the heat burning up your skin.\n" +
               "You all start looking for shelter when suddenly you feel the ground shake.\n" +
               "Kelyan says it's nothing so you all ignore it and keep walking.\n" +
               "The ground starts shaking intensely, and then you see a huge tail coming out of the sand.\n" +
               "Himanshu screams: There's a huge sandworm!\n"+
               "Choose or Die: Stay or Run?")
    
    # Island Scenario
    island = ("\nWELCOME TO THE FIRST STAGE.\n" +
               "You and your 3 friends (Kelyan, Himanshu, and Johnny) are in the middle of an island.\n" +
               "You are all confused and disoriented. You all want answers but survival takes priority.\n" +
               "Kelyan suggests that first, you should find food and water.\n" +
               "It's going to get dark very soon, and in the darkness no one is safe.\n" +
               "Choose or Die: Food or Water?")

    # List of scenarios
    list_of_scenarios = [forest, dessert, island]

    # Use random to choose a random scenario each time the code runs.
    random_scenario = r.choice(list_of_scenarios)
    
    # For loop to type text for the random scenario.
    for char in random_scenario:
        t.sleep(0.05)
        s.stdout.write(char)
        s.stdout.flush()
    s.stdout.write('\n')
    
    # Random Forest Scenario
    if random_scenario == forest:
        # User input.
        user_stage1 = input("> ").lower()
        
        # User chooses to go left or 1.
        if user_stage1 == "left" or user_stage1 == "1":
            text5 = ("\nYou all run left but the path is filled with roots and sharp rocks.\n" +
                    "The demon is getting closer. You are all panicking.\n" +
                    "You see a motorcycle with the keys still in.\n" +
                    "It cannot carry more than 3 people.\n" +
                    "Choose or Die: Johnny, Kelyan, or Himanshu?")
            
            # For loop to type text 5.
            for char in text5:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')
            
            # User input for sacrifice. Users do not know for sure until they choose.
            sacrifice = input("> ").lower()

            # Johnny will be sacrificed.
            if sacrifice == "johnny" or sacrifice == "1":
                text6 = ("\nYou chose to sacrifice Johnny.\n" +
                    "Johnny stays behind, screaming as the demon gets closer.\n" +
                    "You drive away, hearing Johnny's final screams. He's dead.\n" +
                    "You, Kelyan, and Himanshu survived.")
                
                # For loop to type text 6.
                for char in text6:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Leads to stage 2.
                stage_2()
            
            # Kelyan will be sacrificed.
            elif sacrifice == "kelyan" or sacrifice == "2":
                text7 = ("\nYou chose to sacrifice Kelyan.\n" +
                    "You leave Kelyan behind. He starts crying as the demon catches up.\n" + 
                    "'I thought we were friends' he screamed before going silent forever. He's dead.\n" +
                    "You, Johnny, and Himanshu are safe.")
                
                # For loop to type text 7.
                for char in text7:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Leads to stage 2.
                stage_2()

            # Himanshu will be sacrificed.
            elif sacrifice == "himanshu" or sacrifice == "3":
                text8 = ("\nYou chose to sacrifice Himanshu.\n" +
                    "You drove away leaving him behind. Himanshu tries to fight back.\n" + 
                    "But the demon decapitates him in one blow. He's dead.\n" +
                    "You, Johnny, and Kelyan got away.")
                
                # For loop to type text 8.
                for char in text8:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Leads to stage 2.
                stage_2()
            
            # If the user inputs an invalid option, the user fails.
            else:
                text9 = ("Your hesitation has cost all of your lives. The demon catches up. You all die.")

                # For loop to type text 9.
                for char in text9:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to fail function.
                fail()

        # User chooses to go right
        elif user_stage1 == "right" or user_stage1 == "2":
            text10 = ("\nYou run to the right and find an old rope bridge hanging over a deep canyon.\n" +
                "The demon is right behind you, and the bridge can only hold the weight of two people at a time.\n" +
                "Choose or Die: Johnny, Kelyan, or Himanshu?")
            
            # For loop to type text 10.
            for char in text10:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')
            
            # Input to choose who to go with. Users don't know what the choice is for until they choose.
            go_with = input("> ").lower()

            # User chooses to go with Johnny.
            if go_with == "johnny" or go_with == "1":
                text11 = ("\nYou and Johnny cross the bridge first, while Kelyan and Himanshu stay behind.\n" +
                        "Right after you're done crossing, they try to cross.\n" +
                        "But you and Johnny took too long. The demon catches up.\n" +
                        "Himanshu decides to give Kelyan a chance to live and sacrifices himself.\n" +
                        "He tried to fight the demon and died. He's dead.")
                
                # For loop to type text 11.
                for char in text11:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Go to stage 2.
                stage_2()
            
            # User chooses to go with Kelyan.
            elif go_with == "kelyan" or go_with == "2":
                text12 = ("\nYou and Kelyan cross the bridge while Johnny and Himanshu stay behind.\n" +
                        "You crossed really fast. Now it's their turn.\n" +
                        "While trying to cross, Johnny slipped and fell from the bridge. He's dead.\n")
                
                # For loop to type text 12.
                for char in text12:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 2.
                stage_2()

            # User chooses to go with Himanshu.
            elif go_with == "himanshu" or go_with == "3":
                text13 = ("\nYou and Himanshu cross the bridge while Johnny and Kelyan stay behind.\n" +
                        "You crossed the bridge just fine. Now it's their turn.\n" +
                        "Johnny and Kelyan try to cross but their combined weight is too much for the bridge.\n" +
                        "Johnny reacted fast and ran to cross. Kelyan was too slow.\n" +
                        "The bridge came down with him. He's dead.")
                
                # For loop to type text 13.
                for char in text13:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 2.
                stage_2()

            # User doesn't choose.
            else:
                text14 = ("Your hesitation costs everyone their lives. The demon catches up. You all die.")

                # For loop to type text 14.
                for char in text14:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Fails the game.
                fail()

        else:
            # Fails the game.
            fail()

    # Random Dessert scenario.
    elif random_scenario == dessert:

        # User first choice.
        dessert_input1 = input("> ").lower()

        # If user chooses to stay.
        if dessert_input1 == "stay" or dessert_input1 == "1":
            
            # Text description of story.
            dessert_text1 = ("\nYou tell everyone to stay and not move.\n" +
                             "The worm can't detect you anymore but it stays in the area.\n" +
                             "You've been waiting there for 3 hours and the worm is still there.\n" +
                             "You notice that Johnny doesn't look very good. He is dehydrated and about to pass out.\n" +
                             "You have a bottle of water but you are too far.\n"
                             "Choose or Die: Throw or Stay")
            
            # For loop to type dessert_text1.
            for char in dessert_text1:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

            # User second choice.
            dessert_input2 = input("> ").lower()

            # If second choice is to throw.
            if dessert_input2 == "throw" or dessert_input2 == "1":

                # Story text.
                dessert_text2 = ("\nYou throw the bottle at Johnny, but you have terrible aim and it drops close to Himanshu.\n" +
                                 "The worm sensed that and ate Himanshu. He's dead.\n" +
                                 "You all start running. You and your remaining friends got away.")
                
                 # For loop to type dessert_text2.
                for char in dessert_text2:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 2.
                stage_2()

            # If user chooses to stay.
            elif dessert_input2 == "stay" or dessert_input2 == "2":

                # Story text.
                dessert_text3 = ("\nThe worm doesn't leave. Johnny is too tired and passed out.\n" +
                                 "The worm sensed his fall and ate him. He's dead.\n" +
                                 "The rest of you take advantage of the situation and run away.\n")
                
                 # For loop to type dessert_text2.
                for char in dessert_text3:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 2.
                stage_2()
            
            else:
                
                # Fails to choose.
                fail()
        
        # If user chooses to run.
        elif dessert_input1 == "run" or dessert_input1 == "2":

            # Story text.
            dessert_text4 = ("\nYou all start running but the worm can sense your movement.\n" +
                             "You run as fast as you can but the sand makes it hard to run.\n" +
                             "The ground suddenly stops shaking. Kelyan thinks the worm is gone.\n" +
                             "Suddenly, the worm comes out from under the sand and eats Kelyan. He's dead.\n" +
                             "The rest of you run as fast as possible and manage to get away.")

             # For loop to type dessert_text4.
            for char in dessert_text4:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')
            
            # Goes to stage 2.
            stage_2()

        else:

            # Fails to choose.
            fail()
    
    # Random Island scenario.
    elif random_scenario == island:

        # User first Island choice.
        island_input1 = input("> ").lower()

        # If user chooses food.
        if island_input1 == "food" or island_input1 == "1":
            
            # Story text.
            island_text1 = ("\nYou decide to go search for food. After looking for a few hours and not finding anything.\n" +
                            "Himanshu has an amazing idea. 'Why don't we try to catch a fish or crabs on the shore', he said.\n" +
                            "He builds a few fishing rods so you can all fish. However, none of you managed to catch anything.\n" + 
                            "Johnny suggests going deeper into the ocean.\n" +
                            "Choose or Die: Go or Not?")

             # For loop to type island_text1.
            for char in island_text1:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

            # User island second choice.
            island_input2 = input("> ").lower()

            # If user chooses to go in deeper.
            if island_input2 == "go" or island_input2 == "1":
                
                # Story text.
                island_text2 = ("\nJohnny goes deeper to try to catch some fish. He does not catch anything.\n" +
                                "He goes even deeper into the ocean until he eventually manages to catch some crabs.\n" +
                                "You are all happy that you can finally eat. While Johnny was trying to get back to the beach.\n" +
                                "A hungry shark comes and bites his leg off. He managed to get back to the beach with the crabs.\n" +
                                "However, he continued to bleed until he died. He's dead.\n" +
                                "The rest of you managed to survive and continued the journey.")
                
                 # For loop to type island_text2.
                for char in island_text2:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 2.
                stage_2()

            # If user chooses not to go deeper.
            elif island_input2 == "not" or island_input2 == "2":

                # Story text.
                island_text3 = ("\nYou decide that it is too dangerous to go deeper so you all turn around and keep searching in the island.\n" +
                                "You decided to go search on the other side of the island. When you arrive there, you find a tribe of people.\n" +
                                "They seem friendly and give you a little food, just enough to fill your bellies and feel comfortable.\n" +
                                "All of a sudden, one of them attacks Himanshu and knocks him out.\n" +
                                "They are cannibals! They take Himanshu and the rest of you run away. He's dead.")

                 # For loop to type island_text3.
                for char in island_text3:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 2.
                stage_2()

            else:
                # User failed to choose,
                fail()

        # If user chooses to search for water.
        elif island_input1 == "water" or island_input1 == "2":

            # Story text.
            island_text4 = ("\nAfter searching for many hours, you found nothing.\n" + 
                            "Kelyan is so dehydrated that he suggests drinking water from the sea.\n" +
                            "You are all extremely dehydrated and if you don't get water soon, you will all die.\n"
                            "Choose or Die: Search or Drink?")

             # For loop to type island_text4.
            for char in island_text4:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

            # User island second choice.
            island_input3 = input("> ").lower()

            if island_input3 == "search" or island_input3 == "1":
            
                island_text5 = ("\nYou keep searching until you find some coconut trees.\n" +
                                "The trees are very tall and you are all tired. Kelyan offers to climb the tree.\n" +
                                "He reaches the coconuts and cuts them down but he is very tired.\n" +
                                "As he was climbing down, he lost strength in his grip and fell. He's dead.\n" +
                                "The rest of you survived and continued the journey.")

                 # For loop to type island_text5.
                for char in island_text5:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 2.
                stage_2()

            # If user chooses to drink seawater. Terrible idea!!!!!
            elif island_input3 == "drink" or island_input3 == "2":

                # Story text.
                island_text5 = ("\nYou all drink sea water and die soon after.")

                 # For loop to type island_text5.
                for char in island_text5:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # User fails.
                fail()

            else:
                # User fails to choose.
                fail()

        else:
            # User failed to choose.
            fail()
    
    else:
        # Code continues.
        pass

# Stage 2
def stage_2():

    # Intro to stage 2.
    text15 = ("\nYOU ARE NOW IN THE SECOND STAGE.\n" +
              "You and your remaining friends have reached a mysterious cabin.\n"
              "But don't feel too comfortable. You can sense evil approaching.\n" +
              "Choose or Die: In or Out?")
    
    # For loop to type text 15.
    for char in text15:
        t.sleep(0.05)
        s.stdout.write(char)
        s.stdout.flush()
    s.stdout.write('\n')

    # First choice of stage 2.
    user_stage2 = input("> ").lower()
    
    # User chooses to go in.
    if user_stage2 == "in" or user_stage2 == "1":

        # List of figures on the door that the user needs to press to solve the puzzle. Each figure represents a specific value based on the number of vertices.
        door_symbols = [["circle", 10],["triangle", 30],["rectangle", 40],["star", 50]]
        # Dictionary that uses numbers as keys so that the code can read both numbers and words as correct inputs.
        symbol_num = {"1":"circle", "2":"triangle", "3":"rectangle", "4":"star"}

        # Puzzle.
        text16 = ("\nTo open the door and enter the cabin you must solve.\n" +
             "The door has 4 figures:\n" +
             "1. Circle\n" +
             "2. Triangle\n" +
             "3. Rectangle\n" +
             "4. Star\n\n" +
            
            # Hint for the puzzle.
             "You must press the figures in a specific pattern to open the door.\n" +
             "As you think of the puzzle, one of your friends sees the number 100 written at the bottom of the door.\n" +
             "He suggests that maybe the vertices of the figures have something to do with the puzzle.\n" +
             "Choose or Die: Choose three symbols to press. One at a time.")
        
        # For loop to type text 16.
        for char in text16:
            t.sleep(0.05)
            s.stdout.write(char)
            s.stdout.flush()
        s.stdout.write('\n')
        
        # User has 2 tries to solve the puzzle.
        tries = 2
        # Answer for the puzzle.
        answer = 100
        
        # While loop to keep it going until tries reach 0.
        while tries > 0:
            # 3 user choices for the puzzle.
            puzzle_1 = input("> ").lower()
            puzzle_2 = input("> ").lower()
            puzzle_3 = input("> ").lower()

            # Goes to the dictionary to see if the user input is a key (number option) to get the value from that key.
            if puzzle_1 in symbol_num:
                puzzle_1 = symbol_num[puzzle_1] # Key 1.
            if puzzle_2 in symbol_num:
                puzzle_2 = symbol_num[puzzle_2] # Key 2.
            if puzzle_3 in symbol_num:
                puzzle_3 = symbol_num[puzzle_3] # Key 3.

            # Total value that needs to be equal to answer to win.
            total_val = 0

            # Goes to the list to grab the values for each figure.
            for sym, num in door_symbols:
                if puzzle_1 == sym:
                    total_val += num    # Adds value of figure of the first choice to total_value.
                if puzzle_2 == sym:
                    total_val += num    # Adds value of figure of the second choice to total_value.
                if puzzle_3 == sym:
                    total_val += num    # Adds value of figure of the third choice to total_value.
            
            # Checks if total_value equals answer.
            if total_val == answer:
                text17 = ("\nThe door has opened. You all go inside the cabin.\n" +
                          "But before closing the door, a demon grabs one of your friend's feet and drags him out.\n" +
                          "You hear his screams. He's death.")
                
                # For loop to type text 17.
                for char in text17:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

                # Goes to stage 3.
                stage_3()
                break

            # Answer is incorrect so one less try.
            else:
                tries -= 1
                # Print this while tries is not 0.
                if tries > 0:
                    text18 = ("\nThe combination is wrong and the door is still locked. Try again but hurry.\n" +
                              "Monsters are coming.")
                    
                    # For loop to type text 18.
                    for char in text18:
                        t.sleep(0.05)
                        s.stdout.write(char)
                        s.stdout.flush()
                    s.stdout.write('\n')
        
        # No more tries.
        if tries == 0:
            text19 = ("\nIt's too late. A demon suddenly appeared and killed all of you.")

            # For loop to type text 19.
            for char in text19:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

            # Fails the game.
            fail()
            
        else:
            # Code continues.
            pass

    # User chooses to go out.
    elif user_stage2 == "out" or user_stage2== "2":
        text20 = ("\nYou decide to ignore the cabin and continue.\n" +
                  "Suddenly, a storm arrives. It's very strong, with constant lightning and freezing rain and wind.\n" +
                  "You got hit by lightning, and your friends froze soon after. None of you survived the storm.")
        
        # For loop to type text 20.
        for char in text20:
            t.sleep(0.05)
            s.stdout.write(char)
            s.stdout.flush()
        s.stdout.write('\n')

        # Fails the game.
        fail()

    else:
        # Fails the game.
        fail()

# Stage 3. Final Stage.
def stage_3():
    text21 = ("\nYOU ARE NOW IN THE FINAL STAGE.\n" +
              "You and your last friend are inside the cabin.\n"
              "The place is very quiet and dark.\n" +
              "Suddenly, the lights dim, and a voice echoes through the room:\n" +
              "'Congratulations for making it this far. But the game isn't over yet.'\n\n" +
              "You look around to find who's speaking.\n" +

              "Sitting on a throne is Erick.\n\n"
              "'I am the game master and I brought you all here to feed the game.\n" +
              "But we're not done yet. There is one last choice you must make.'\n"
              "Erick said with a sinister smile.\n\n" +
            
              "Then he pulls a lever and a trap door opens right below your last remaining friend.\n" +
              "He's been fed to the game. He's death.\n\n" +

              "You have lost all your friends. You cry and scream.\n" +
              "As you struggle to believe this new reality.\n" +
              "A gun appears right in front of you with only one bullet.\n" +
              "'Choose or Die: Who do you want to shoot? Erick or You")
    
    # For loop to type text 22.
    for char in text21:
        t.sleep(0.05)
        s.stdout.write(char)
        s.stdout.flush()
    s.stdout.write('\n')

    # The final choice.
    # While loop to keep the user until he chooses.
    while True:
        final_choice = input("> ").lower()

        # User chooses to shoot Erick.
        if final_choice == "erick" or final_choice == "shoot erick" or final_choice == "1":
            text22 = ("\nYou take the gun, aim it at Erick, and pull the trigger.\n" +
                      "The bullet reaches Erick. It's a headshot... but wait...\n" +
                      "He is fine and you start bleeding. Erick smiled and said:\n\n" +
                      "'Hahaha you did not uncover the true puzzle of the game. This game is about making the player suffer.\n" +
                      "To win you had to shoot yourself.'\n\n"
                      "You die from your own shot. Erick walks away, victorious.\n")
            
            # For loop to type text 22.
            for char in text22:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

            # User fails.
            fail()
            break

        # User chooses to shoot himself.
        elif final_choice == "me" or final_choice == "shoot me" or final_choice == "myself" or final_choice == "shoot myself" or final_choice == "2":
            text23 = ("\nYou look at the gun and realize that there is no point in winning the game now that you're alone.\n" +
                      "You turn the gun at yourself and pull the trigger.\n\n" +
                      "But you don't feel anything. Instead, Erick drops to the ground. He's death.\n")
            
            # For loop to type text 23.
            for char in text23:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

            # User wins.
            win()
            break

        # Easter Egg choice.
        elif final_choice == "chase":
            easteregg()
            break

        # Keep the user in the loop.
        else:
            text24 = ("You must choose.\n")

            # For loop to type text 24.
            for char in text24:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

# Easter Egg Chase.
def easteregg():
    t_chase = ("\nYou scream 'CHASEEEE!!'. This summons Professor Chase.\n" +
               "But before the summon can be completed, you must guess the random lucky number between 1-10.\n" +
               "You have 5 tries. What's your guess?")
    
    # For loop to type t_chase.
    for char in t_chase:
        t.sleep(0.05)
        s.stdout.write(char)
        s.stdout.flush()
    s.stdout.write('\n')

    # Generate random number.
    random_lucky_num = r.randint(1, 10)
    # Number of tries.
    tries = 5

    # Loop to keep the player guessing until there are no more tries
    while tries > 0:
        user_guess = input("> ")

        # Check for errors. In case the input is not an integer.
        try:
            # Convert input to integer
            user_guess = int(user_guess)
            
            # Check if guess is the lucky number
            if user_guess == random_lucky_num:
                t_chase1 = ("\nYou successfully summon Chase. He appears and slaps both of you, which takes you all out of the game.\n" +
                            "He also uses his Python superpowers to overwrite the game and bring your friends back.\n" +
                            "Congratulations! You all survived.")
                
                # For loop to type t_chase1.
                for char in t_chase1:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')
                
                # Create big message for Chase Ending.
                easteregg_print = p.figlet_format("Chase Ending", font="starwars")
                print(easteregg_print)
                
                return  # Exit the function. End of game.

            # Incorrect guess.
            else:
                # -1 guess every time it's wrong.
                tries -= 1
                text_try = (f"Try again. You have {tries} tries left.")

                # For loop to type text_try.
                for char in text_try:
                    t.sleep(0.05)
                    s.stdout.write(char)
                    s.stdout.flush()
                s.stdout.write('\n')

        # If the input is not an integer. Try again.
        except ValueError:
            text_error = "That is not a valid answer. Enter an integer."

            # For loop to type text_error.
            for char in text_error:
                t.sleep(0.05)
                s.stdout.write(char)
                s.stdout.flush()
            s.stdout.write('\n')

    # No more tries.
    if tries == 0:
        text_tryagaing = ("You didn't get it and so you are taken back to the start of stage 3.")

         # For loop to type text_tryagaing.
        for char in text_tryagaing:
            t.sleep(0.05)
            s.stdout.write(char)
            s.stdout.flush()
        s.stdout.write('\n')

        # Takes the user back to the start of stage 3.
        stage_3()
        
    else:
        # Code continues.
        pass

# User wins.
def win():
    text25 = ("Congratulations! You have won the game. You will receive an A in Python.\n" +
              "You are now the new game master.\n" +
              "Choose the next player. The more they suffer the more you benefit.")
    
    # For loop to type text 25.
    for char in text25:
        t.sleep(0.05)
        s.stdout.write(char)
        s.stdout.flush()
    s.stdout.write('\n')

    # Create big win message.
    easteregg_print = p.figlet_format("Congrats Game Master", font="epic")

    # Print message.
    print(easteregg_print)
    
# User fails and dies.
def fail():
    text26 = ("You have chosen death. Are you ready to die?\n" + 
         "HAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHA")
    
    # For loop to type text 26.
    for char in text26:
        t.sleep(0.05)
        s.stdout.write(char)
        s.stdout.flush()
    s.stdout.write('\n')
    
    # Create a big "Game Over" message with a creepy font.
    game_over = p.figlet_format("Game Over", font="poison")

    # Print message.
    print(game_over)

# Start Game.
start()