const carros = [
            { id: 1, modelo: "Chevrolet Onix", placa: "ABC-1234", ano: 2022, cor: "Preto", disponivel: true, preco: 120.00, imagem: "imagens/carro1.jpg" },
            { id: 2, modelo: "Fiat Argo", placa: "DEF-5678", ano: 2021, cor: "Branco", disponivel: false, preco: 110.00, imagem: "imagens/carro2.jpg" },
            { id: 3, modelo: "Honda Civic", placa: "GHI-9101", ano: 2020, cor: "Prata", disponivel: true, preco: 180.00, imagem: "imagens/carro3.jpg" },
            { id: 4, modelo: "Toyota Corolla", placa: "JKL-1122", ano: 2023, cor: "Azul", disponivel: true, preco: 190.00, imagem: "imagens/carro4.jpg" },
            { id: 5, modelo: "Volkswagen Gol", placa: "MNO-3344", ano: 2022, cor: "Vermelho", disponivel: false, preco: 100.00, imagem: "imagens/carro5.jpg" },
            { id: 6, modelo: "Ford Ka", placa: "PQR-5566", ano: 2021, cor: "Preto", disponivel: true, preco: 105.00, imagem: "imagens/carro6.jpg" },
            { id: 7, modelo: "Nissan Sentra", placa: "STU-7788", ano: 2020, cor: "Branco", disponivel: true, preco: 160.00, imagem: "imagens/carro7.jpg" },
            { id: 8, modelo: "Hyundai HB20", placa: "VWX-9900", ano: 2021, cor: "Azul", disponivel: false, preco: 115.00, imagem: "imagens/carro8.jpg" },
            { id: 9, modelo: "Renault Kwid", placa: "XYZ-1234", ano: 2023, cor: "Prata", disponivel: true, preco: 90.00, imagem: "imagens/carro9.jpg" },
            { id: 10, modelo: "Jeep Compass", placa: "ABC-5678", ano: 2022, cor: "Verde", disponivel: false, preco: 220.00, imagem: "imagens/carro10.jpg" },
            { id: 11, modelo: "Peugeot 208", placa: "DEF-9101", ano: 2021, cor: "Amarelo", disponivel: true, preco: 130.00, imagem: "imagens/carro11.jpg" },
            { id: 12, modelo: "Chevrolet Spin", placa: "GHI-1122", ano: 2020, cor: "Cinza", disponivel: true, preco: 150.00, imagem: "imagens/carro12.jpg" },
            { id: 13, modelo: "Honda Fit", placa: "JKL-3344", ano: 2023, cor: "Branco", disponivel: false, preco: 140.00, imagem: "imagens/carro13.jpg" },
            { id: 14, modelo: "Toyota Etios", placa: "MNO-5566", ano: 2022, cor: "Vermelho", disponivel: true, preco: 125.00, imagem: "imagens/carro14.jpg" },
            { id: 15, modelo: "Volkswagen Polo", placa: "PQR-7788", ano: 2021, cor: "Preto", disponivel: false, preco: 135.00, imagem: "imagens/carro15.jpg" },
            { id: 16, modelo: "Citroën C3", placa: "STU-9900", ano: 2020, cor: "Azul", disponivel: true, preco: 120.00, imagem: "imagens/carro16.jpg" },
            { id: 17, modelo: "Ford Fiesta", placa: "VWX-1234", ano: 2023, cor: "Cinza", disponivel: true, preco: 145.00, imagem: "imagens/carro17.jpg" },
            { id: 18, modelo: "Nissan Kicks", placa: "XYZ-5678", ano: 2022, cor: "Verde", disponivel: false, preco: 200.00, imagem: "imagens/carro18.jpg" },
            { id: 19, modelo: "Renault Duster", placa: "ABC-9101", ano: 2021, cor: "Preto", disponivel: true, preco: 170.00, imagem: "imagens/carro19.jpg" },
            { id: 20, modelo: "BMW X1", placa: "DEF-1122", ano: 2020, cor: "Branco", disponivel: false, preco: 280.00, imagem: "imagens/carro20.jpg" }
        ];

        function preencherCarrosDisponiveis() {
            const selectCarro = document.getElementById('selectCarro');
            
            const carrosDisponiveis = carros.filter(carro => carro.disponivel === true);
            
            carrosDisponiveis.sort((a, b) => a.modelo.localeCompare(b.modelo));
            
            while (selectCarro.options.length > 1) {
                selectCarro.remove(1);
            }
            
            carrosDisponiveis.forEach(carro => {
                const option = document.createElement('option');
                option.value = carro.id;
                option.textContent = `${carro.modelo} - ${carro.placa} (${carro.ano}, ${carro.cor}) - R$ ${carro.preco.toFixed(2)}/dia`;
                selectCarro.appendChild(option);
            });
            
            if (carrosDisponiveis.length === 0) {
                const option = document.createElement('option');
                option.value = "";
                option.textContent = "Nenhum carro disponível no momento";
                option.disabled = true;
                selectCarro.appendChild(option);
            }
        }

        function configurarAtualizacaoValor() {
            const selectCarro = document.getElementById('selectCarro');
            const inputValor = document.querySelector('input[type="number"]');
            const inputDataRetirada = document.querySelectorAll('input[type="date"]')[0];
            const inputDataDevolucao = document.querySelectorAll('input[type="date"]')[1];
            
            function calcularValorTotal() {
                if (selectCarro.value && inputDataRetirada.value && inputDataDevolucao.value) {
                    const carroSelecionado = carros.find(carro => carro.id === parseInt(selectCarro.value));
                    if (carroSelecionado) {
                        const dataRetirada = new Date(inputDataRetirada.value);
                        const dataDevolucao = new Date(inputDataDevolucao.value);
                        
                        const diffTime = Math.abs(dataDevolucao - dataRetirada);
                        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
                        
                        const dias = diffDays > 0 ? diffDays : 1;
                        const valorTotal = dias * carroSelecionado.preco;
                        
                        inputValor.value = valorTotal.toFixed(2);
                    }
                }
            }
            
            selectCarro.addEventListener('change', calcularValorTotal);
            inputDataRetirada.addEventListener('change', calcularValorTotal);
            inputDataDevolucao.addEventListener('change', calcularValorTotal);
            
            const hoje = new Date().toISOString().split('T')[0];
            inputDataRetirada.min = hoje;
            
            inputDataRetirada.addEventListener('change', function() {
                inputDataDevolucao.min = this.value;
            });
        }

        function configurarFormulario() {
            const formAgendamento = document.getElementById('formAgendamento');
            const mensagemAgendamento = document.getElementById('mensagemAgendamento');
            
            formAgendamento.addEventListener('submit', function(event) {
                event.preventDefault();
                
                const dataRetirada = new Date(formAgendamento.querySelectorAll('input[type="date"]')[0].value);
                const dataDevolucao = new Date(formAgendamento.querySelectorAll('input[type="date"]')[1].value);
                
                if (dataDevolucao <= dataRetirada) {
                    mensagemAgendamento.textContent = "Erro: A data de devolução deve ser posterior à data de retirada!";
                    mensagemAgendamento.style.display = 'block';
                    mensagemAgendamento.style.backgroundColor = '#f8d7da';
                    mensagemAgendamento.style.color = '#721c24';
                    return;
                }
                
                mensagemAgendamento.textContent = "Agendamento salvo com sucesso!";
                mensagemAgendamento.style.display = 'block';
                mensagemAgendamento.style.backgroundColor = '#d4edda';
                mensagemAgendamento.style.color = '#155724';
                
                console.log('Dados do agendamento:', {
                    clienteId: formAgendamento.querySelector('.inputSelecao').value,
                    carroId: document.getElementById('selectCarro').value,
                    dataRetirada: dataRetirada,
                    dataDevolucao: dataDevolucao,
                    valorTotal: formAgendamento.querySelector('input[type="number"]').value,
                    status: formAgendamento.querySelectorAll('.inputSelecao')[1].value
                });
                
                setTimeout(() => {
                    formAgendamento.reset();
                    mensagemAgendamento.style.display = 'none';
                }, 3000);
            });
        }

        document.addEventListener('DOMContentLoaded', function() {
            preencherCarrosDisponiveis();
            configurarAtualizacaoValor();
            configurarFormulario();
        });