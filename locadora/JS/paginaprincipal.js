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
            { id: 11, modelo: "Peugeot 2008", placa: "DEF-9101", ano: 2021, cor: "Amarelo", disponivel: true, preco: 130.00, imagem: "imagens/carro11.jpg" },
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
        
        function renderizarCarros() {
            const gradeCarros = document.getElementById('gradeCarros');
            gradeCarros.innerHTML = '';
            
            carros.forEach(carro => {
                let marca = carro.modelo.split(' ')[0];
                
                const card = document.createElement('div');
                card.className = 'cardCarro';
                
                const imagensPlaceholder = [
                    'imagens/carro1.jpg',
                    'imagens/carro2.jpg',
                    'imagens/carro3.jpg',
                    'imagens/carro4.jpg',
                    'imagens/carro5.jpg',
                    'imagens/carro6.jpg',
                    'imagens/carro7.jpg',
                    'imagens/carro8.png',
                    'imagens/carro9.png',
                    'imagens/carro10.jpg',
                    'imagens/carro11.jpg',
                    'imagens/carro12.jpg',
                    'imagens/carro13.jpg',
                    'imagens/carro14.jpg',
                    'imagens/carro15.jpg',
                    'imagens/carro16.jpg',
                    'imagens/carro17.jpg',
                    'imagens/carro18.jpg',
                    'imagens/carro19.jpg',
                    'imagens/carro20.jpg',
                
                ];
                
                const imagemIndex = (carro.id - 1) % imagensPlaceholder.length;
                
                card.innerHTML = `
                    <img src="${imagensPlaceholder[imagemIndex]}" alt="${carro.modelo}" class="imagemCarro">
                    <div class="conteudoCard">
                        <h3 class="nomeCarro">${carro.modelo}</h3>
                        <div class="infoCarro">
                            <span>${marca} • ${carro.ano} • ${carro.cor}</span>
                        </div>
                        <div class="infoCarro">
                            <span>Placa: ${carro.placa}</span>
                        </div>
                        <div class="precoCarro">R$ ${carro.preco.toFixed(2)}<small>/dia</small></div>
                        <span class="statusCarro ${carro.disponivel ? 'disponivel' : 'indisponivel'}">
                            ${carro.disponivel ? 'Disponível' : 'Indisponível'}
                        </span>
                        <div class="areaAcoes">
                            <a href="cadastroAgendamento.html" class="botaoReservar ${carro.disponivel ? '' : 'indisponivel'}" ${carro.disponivel ? '' : 'style="opacity:0.6; cursor:not-allowed;"'}>
                                ${carro.disponivel ? 'Reservar Agora' : 'Indisponível'}
                            </a>
                            <a href="#" class="botaoDetalhes" onclick="verDetalhes(${carro.id})">Ver Detalhes</a>
                        </div>
                    </div>
                `;
                
                gradeCarros.appendChild(card);
            });
        }
        
        function verDetalhes(id) {
            const carro = carros.find(c => c.id === id);
            if (carro) {
                alert(`Detalhes do Carro:\n\nModelo: ${carro.modelo}\nPlaca: ${carro.placa}\nAno: ${carro.ano}\nCor: ${carro.cor}\nPreço diária: R$ ${carro.preco.toFixed(2)}\nStatus: ${carro.disponivel ? 'Disponível' : 'Indisponível'}`);
            }
        }
        
        document.addEventListener('DOMContentLoaded', renderizarCarros);