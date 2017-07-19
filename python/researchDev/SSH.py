from paramiko import SSHClient

def get_ssh_clint(self):
    # Create ssh client.
    ssh = SSHClient()
    #ssh.set_missing_host_key_policy(AutoAddPolicy())

    # With Credentials
    SSH_SERVER = '192.168.4.15'
    SSH_USERNAME = 'administrator'
    SSH_PASSWORD = 'Password1'
    ssh.connect(SSH_SERVER, username=SSH_USERNAME, password=SSH_PASSWORD)
    # With private key
    #key = RSAKey.from_private_key_file("/path/key.pem")
    #ssh.connect(SSH_SERVER, username=SSH_USERNAME, pkey = key )


    entry = entry.rstrip()
    cmd_to_execute = powerShellString + ' ' + entry + '"'
    print ( "Executing command- " + cmd_to_execute )
    print
    ssh_stdin, ssh_stdout, ssh_stderr = ssh.exec_command(cmd_to_execute)
    for line in ssh_stdout:
        line = line.rstrip()
    ssh.close()
