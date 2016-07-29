## Author: John Pierce
## Contributor: Rod Soto


from paramiko import SSHClient, AutoAddPolicy, RSAKey
import argparse
import os

parser = argparse.ArgumentParser()
parser.add_argument("-f", "--file", help="Text file containing one filename per line.")
parser.add_argument("-n", "--name", help="The name of a single file to block.")

args = parser.parse_args()
fileList = []

if not (args.file or args.name):
    raise Exception("Error: no file supplied. Please use -n to specify a file or -f to specify a file containing "
                    "the names of files to block.")
elif args.file and args.name:
    raise Exception("Error: -n and -f are mutually exclusive.  Use only one when executing.")
elif args.name:
    fileList.append(args.name)
elif not os.path.isfile(args.file):
    raise Exception("Error- " + args.file + " is not a valid file.")
else:
    file = open(args.file, 'r')
    for entry in file:
        fileList.append(entry)
    file.close()

powerShellString = 'C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell -InputFormat none -OutputFormat TEXT ' \
                   '-command "Import-Module grouppolicy; ' \
                   'Set-GPRegistryValue -Name antimal ' \
                   '-Key HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun ' \
                   '-ValueName 1 -Type String -Value'

# Create ssh client.
ssh = SSHClient()
ssh.set_missing_host_key_policy(AutoAddPolicy())

# With Credentials
SSH_SERVER = '192.168.4.15'
SSH_USERNAME = 'administrator'
SSH_PASSWORD = 'Password1'
ssh.connect(SSH_SERVER, username=SSH_USERNAME, password=SSH_PASSWORD)

# With private key
#key = RSAKey.from_private_key_file("/path/key.pem")
#ssh.connect(SSH_SERVER, username=SSH_USERNAME, pkey = key )


for entry in fileList:
    entry = entry.rstrip()
    cmd_to_execute = powerShellString + ' ' + entry + '"'
    print "Executing command- " + cmd_to_execute
    print
    ssh_stdin, ssh_stdout, ssh_stderr = ssh.exec_command(cmd_to_execute)
    for line in ssh_stdout:
        line = line.rstrip()
        print line
ssh.close()

