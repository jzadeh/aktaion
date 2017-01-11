# Aktaion: Open Source Tool For "Micro Behavior Based" Exploit Detection and Automated GPO Policy Generation

Aktaion is a lightweight JVM based project for detecting exploits (and more generally attack behaviors).  The project is meant to be a learning/teaching tool on how to blend multiple sectuirty singals and behaviors into an expressive framework for intrusion detection.  The cool about the project is it provides an expressive mechanism to add high level IOCs (micro beahviors) such as timing behavior of a certain malware family.  

<http://www.github.com/jzadeh/Atkaion>


## Online Documentation

You can find the latest Spark documentation, including a programming
guide, on the [project web page](http://ttp://www.github.com/jzadeh/Atkaion)
This README file only contains basic setup instructions.

## Building Atkaion

Atkaion is built using [Simple Build Tool](http://www.scala-sbt.org//).
To build Atkaion use the assembly command via:

    sbt assembly


## Dependencies

To run the jar from the command line the following dependencies are required for scoring a PCAP:

	BRO
	Java 1.8


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





