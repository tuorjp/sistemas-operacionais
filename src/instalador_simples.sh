#!/bin/bash

# --------------------------------------------
# Instalador Simples - Simulação
# Autor: JP
# Descrição: Cria estrutura de diretórios, arquivos, escreve conteúdo e define permissões
# --------------------------------------------

echo "Iniciando a instalação do Aplicativo Exemplo..."

# 1. Criação da estrutura de diretórios
echo "Criando diretórios..."
mkdir -p ~/meu_app/bin
mkdir -p ~/meu_app/config
mkdir -p ~/meu_app/logs

# 2. Criação de arquivos
echo "Criando arquivos..."
touch ~/meu_app/README.txt
touch ~/meu_app/config/config.cfg
touch ~/meu_app/bin/start.sh

# 3. Escrita de conteúdos nos arquivos
echo "Escrevendo conteúdo nos arquivos..."
echo "Aplicativo Exemplo v1.0" > ~/meu_app/README.txt
echo "# Configurações do aplicativo" > ~/meu_app/config/config.cfg
echo "#!/bin/bash" > ~/meu_app/bin/start.sh
echo "echo 'Aplicativo Iniciado com Sucesso!'" >> ~/meu_app/bin/start.sh

# 4. Definição de permissões
echo "Definindo permissões..."
chmod +r ~/meu_app/README.txt
chmod +rw ~/meu_app/config/config.cfg
chmod +x ~/meu_app/bin/start.sh

# 5. Finalização
echo "Instalação concluída!"
echo "Execute '~/meu_app/bin/start.sh' para iniciar o aplicativo."
