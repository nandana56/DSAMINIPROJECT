package Familytreegenerator;
import java.util.*;


class Person {
String name;
Person[] children;

public Person(String name, int maxChildren) {
this.name = name;
this.children = new Person[maxChildren]; //arr[]= {11,12,13}
}
}

class FamilyTree {
Person root;
int maxChildren;


public FamilyTree(String rootName, int maxChildren) {
this.root = new Person(rootName, maxChildren);
this.maxChildren = maxChildren;


}

public void addChild(String parentName, String childName) {
Person parent = findPerson(root, parentName);
if (parent != null) {
for (int i = 0; i < maxChildren; i++) {
if (parent.children[i] == null) { 			//[gf, p1, p2,- - - ]
parent.children[i] = new Person(childName, maxChildren);
break;
}
}
}

}

public void displayFamilyTree() {
displayTree(root, 0);
}

public String getRelationship(String person1Name, String person2Name) {
Person person1 = findPerson(root, person1Name);
Person person2 = findPerson(root, person2Name);

if (person1 == null || person2 == null) {

return "No relationship found";
}

if(person1.name.equals(person2.name)){
return "Same person";
}

if (isGrandparentOf(person1, person2)) {  //(gf, Gchild)
return person1Name + " is the grandparent of " + person2Name;
}
else if (isGrandparentOf(person2, person1)) {  //(Gchild, gf)
return person1Name + " is the grandparent of " + person2Name;
}



if (isParentOf(person1, person2)) {  //(c11, c12)
return person1Name + " is the parent of " + person2Name;
}
else if  (isParentOf(person2, person1)){           //(c12,c11)
return person1Name + " is the parent of " + person2Name;
}


if (isSiblings(person1, person2)==1) {     //1=direct siblings

return person1Name + " and " + person2Name + " are siblings2";        //c22, p1
}
else if(isSiblings(person1, person2)==2){   //(p1, c22), (c22,p1) //sibling's childs

return person2Name + " is child of " + person1Name+"'s sibling i.e"+getParent(person2).name;
}
else if(isSiblings(person1,person2)==3 ) {  //cousins
return person1Name + " and " + person2Name + " are siblings2 (cousins)";

}
return "No direct relationship found";
}

private int isSiblings(Person person1, Person person2) {
Person person11=getParent(person1);
Person person22=getParent(person2);

if (getParent(person1)==getParent(person2)) {   //parent of person1== parent of person2

return 1;       //c22, p1
}
//sibling's childs || child of sibling
else if(getParent(person1)==getParent(getParent(person2)) || getParent(person2)==getParent(getParent(person1))){   //(p1, c22), (c22,p1)
//     gf             ==  getParent  (p2)

return 2;
}
else if (isSiblings(person11,person22)==1) {   //for cousins

return 3;       //c22, p1
}
return 0;


}

private boolean isGrandparentOf(Person grandparent, Person grandchild) {
for (Person child : grandparent.children) { //gp-->p1,p2,
if (isParentOf(child, grandchild)) {//p1,p2-->c11,c12,c21,c22
return true;
}
}
return false;
}

private boolean isParentOf(Person parent, Person child) {
return parent != null && Arrays.asList(parent.children).contains(child);
}

private Person getParent(Person child) {
return findParent(root, child);
}

private void displayTree(Person person, int level) {
if (person != null) {
StringBuilder prefix = new StringBuilder();
for (int i = 0; i < level; i++) {
prefix.append("    ");
}
System.out.println(prefix + "└── " + person.name);

for (Person child : person.children) {
displayTree(child, level + 1);
}
}
}

private Person findPerson(Person current, String name) {
if (current == null) {    //no elements
return null;
}

if (current.name.equals(name)) {  //only element
return current;
}

for (Person child : current.children) {   //traverse
Person found = findPerson(child, name);
if (found != null) {
return found;
}
}
return null;
}

private Person findParent(Person current, Person child) {
if (current == null) {
return null;
}

for (Person sibling : current.children) {
if (sibling != null && Arrays.asList(sibling.children).contains(child)) {
return sibling;
} else {
Person parent = findParent(sibling, child);
if (parent != null) {
return parent;
}
}
}
return null;
}
}


public class Main{
public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);

System.out.print("Enter the name of the root person (e.g., Grandfather): ");
String rootName = scanner.nextLine();

System.out.print("Enter the maximum number of children each person can have: ");
int maxChildren = scanner.nextInt();

FamilyTree familyTree = new FamilyTree(rootName, maxChildren);

while (true){
int choice;
System.out.println("\n1. Add family member");
System.out.println("2. Display family tree");
System.out.println("3. Check relationship");
System.out.println("4. Exit");

System.out.print("Choose an option: ");
choice = scanner.nextInt();
scanner.nextLine(); // Consume the newline character

switch (choice) {
case 1:
int check = 1;
System.out.print("Enter the name of the parent: ");
String parentName = scanner.nextLine();
while (check != 0) {
System.out.print("Enter the name of the child: ");
String childName = scanner.nextLine();
familyTree.addChild(parentName, childName);
System.out.print("Do you want to continue adding children of " + parentName + "? Choose Yes/No (1/0): ");
check = scanner.nextInt();
scanner.nextLine();
}
break;

case 2:
System.out.println("\nFamily Tree:");
familyTree.displayFamilyTree();
break;

case 3:
check=1;
System.out.print("Enter the name of the first person: ");
String name1 = scanner.nextLine();
while (check != 0) {
System.out.print("Enter the name of the second person: ");
String name2 = scanner.nextLine();
System.out.println(familyTree.getRelationship(name1, name2));
System.out.print("\nDo you want to find more Relationship with person " + name1 + " -> Yes/No (1/0): ");
check = scanner.nextInt();
scanner.nextLine();
}
break;

case 4:
System.out.println("Exiting the program. Goodbye!");
scanner.close();
System.exit(0);

default:
System.out.println("Invalid choice. Please choose a valid option.");
}
}
}
}
