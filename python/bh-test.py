from paramiko import SSHClient, AutoAddPolicy
import argparse
import os

parser = argparse.ArgumentParser()
parser.add_argument("-f", "--file", help="Text file containing one ioc per line.")

args = parser.parse_args()
if not args.file:
    raise Exception("Error- no file supplied.")
elif not os.path.isfile(args.file):
    raise Exception("Error- " + args.file + " is not a valid file.")
else:
    file = open(args.file, 'r')

# We will replace this with keys once we have a config script
SSH_SERVER = 'server'
SSH_USERNAME = 'user'
SSH_PASSWORD = 'password'

powerShellString = "Set-GPRegistryValue -Name TestGPO2 -Key 'HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\" \
                   "Policies\\Explorer\\DisallowRun' -ValueName '1' -Type String -Value"

# Create ssh client
ssh = SSHClient()
ssh.set_missing_host_key_policy(AutoAddPolicy())
ssh.connect(SSH_SERVER, username=SSH_USERNAME, password=SSH_PASSWORD)
# print 'SSH_SERVER: %s | SSH_USERNAME: %s | SSH_PASSWORD: %s' % (SSH_SERVER, SSH_USERNAME, SSH_PASSWORD)

for entry in file:
    entry = entry.rstrip()
    cmd_to_execute = powerShellString + "'" + entry + "'"
    print "Executing command- " + cmd_to_execute
    ssh_stdin, ssh_stdout, ssh_stderr = ssh.exec_command(cmd_to_execute)
    for line in ssh_stdout:
        print line
ssh.close()
file.close()
