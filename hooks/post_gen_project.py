'''
Rename the template containing dir with the correct namespace.
'''
import os


def package2dirs(package_name):
    return package_name.replace('.', '/')

def rename_template(package_name):
    basedir = 'app/src/main/java/'

    src = os.path.join(basedir, '__template__')
    dst = os.path.join(basedir, package2dirs(package_name))

    os.makedirs(dst)

    os.rename(src, dst)

if __name__ == '__main__':
    rename_template('{{ cookiecutter.package_name }}')
