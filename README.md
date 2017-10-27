# Aktaion: Open Source Tool For "Micro Behavior Based" Exploit Detection and Automated GPO Policy Generation

Aktaion is a lightweight JVM based project for detecting exploits (and more generally attack behaviors).  The project is meant to be a learning/teaching tool on how to blend multiple security signals and behaviors into an expressive framework for intrusion detection.  The key abstraction we wanted to protoype is the idea of a microbehavior.  This concept helps to provide an expressive mechanism to add high level IOCs such as timing behavior of a certain malware family in parrallel to simple statsitcs, rules or anything relevant to building a programmitic descpriotn of a sequential evolving set of advesary behaviors.  

<http://www.github.com/jzadeh/aktaion>


## Online Documentation

You can find the latest Spark documentation, including a programming
guide, on the [project web page](http://ttp://www.github.com/jzadeh/Atkaion)
This README file only contains basic setup instructions.

## Building Atkaion

Atkaion is built using [Simple Build Tool](http://www.scala-sbt.org//).
To build Atkaion use the assembly command via:

    sbt assembly

## Running the test logic/demo

After assembly completes the jar is landed to the target/scala-2.12 subfolder.  The scala version is specific to what is running native so the jar may be landed in target/scala-2.12 depending on the version of the language used in the compilation step.  To run the jar type in the appropriate subfolder:

    java -jar aktaion-assembly-2.0.jar

## Dependencies

To run the jar from the command line the following dependencies are required for scoring a PCAP:

	BRO
	Java 1.8
	Scala 2.12 (2.11 should work as well)

Rough Notes for building on IntelliJ (Mac/Windows):

Step 1:  Download the Java JDK for java 1.8 : java 1.8.0_102-b14
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
 
Step 2:  Install scala
https://www.scala-lang.org/download/2.12.*
 
Step 3:  SBT 0.13.12  
http://www.scala-sbt.org/download.html
 
Step 4: Clone the software repo: https://github.com/jzadeh/Aktaion.git or just copy the indivual build.sbt file at the root of the project and the assembly plugin in the project/ subfolder.  
 
Step 5: Run the command sbt at the root of the directory. 
 
Step 6. Type compile at the sbt prompt if step 5 did not fail(there are no scripts at this point) make sure you don’t hit any erros here either.
 
Step 7: IntelliJ Community Edition (Free)
https://www.jetbrains.com/idea/
 
Step 8:  Install the Scala plugin for IntelliJ
https://confluence.jetbrains.com/display/SCA/Scala+Plugin+for+IntelliJ+IDEA
 
Step 9: Optional install sbt plugin for IntelliJ

For OS X the simple homebrew method has been tested:

Install Homebrew
	ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install )"
	Brew install scala 2.11.8
	Brew install sbt 0.13.8
	Brew install bro
	git clone git@github.com:jzadeh/Aktaion.git
	cd Atkaion
	sbt assembly

Python dependencies 2.70
	Pip install paramiko

Python 2.70 dependencies 
	Pip install paramiko
	https://github.com/unixist/cryptostalker 

Some caveats about the Active Defense Script

- It can be run from Security Onion https://securityonion.net/
- You will need to create a GPO prior to executing the script and reference it in the name (I.E  ' -Name antimal ')
- Script will only work if you have SSH in your Server (You can use FreeSSH or OpenSSH)
- You will have to create an ssh account linked to AD with the proper permissions to execute powershell scripts
   - https://blogs.technet.microsoft.com/heyscriptingguy/2010/11/16/enable-powershell-remoting-to-enable-running-commands/
   - https://technet.microsoft.com/en-us/library/hh849694.aspx
- If this is going to be implemented in production, it is recommended to do it with Service Account. Below the Microsoft recommended steps. 
https://technet.microsoft.com/en-us/library/dd548356(v=ws.10).aspx
