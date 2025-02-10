package com.codelogium.portfolioservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codelogium.portfolioservice.entity.Portfolio;
import com.codelogium.portfolioservice.entity.User;
import com.codelogium.portfolioservice.exception.EntityNotFoundException;
import com.codelogium.portfolioservice.respositry.PortfolioRepository;
import com.codelogium.portfolioservice.respositry.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PortfolioServiceImp implements PortfolioService {
    
    private PortfolioRepository portfolioRespository;
    private UserRepository userRepository;

    @Override
    public Portfolio createPortfolio(Long userId, Portfolio portfolio) {
        User user = UserServiceImp.unwrapUser(userId, userRepository.findById(userId));
        portfolio.setUser(user);
        return portfolioRespository.save(portfolio);
    }

    @Override
    public Portfolio getPortfolio(Long id) {
        return unwrapPortfolio(id, portfolioRespository.findById(id));
    }

    @Override
    public void removePortfolio(Long id) {
        Portfolio portfolio = unwrapPortfolio(id, portfolioRespository.findById(id));
        portfolioRespository.delete(portfolio);
    }

    public static Portfolio unwrapPortfolio(Long id, Optional<Portfolio> optPorfolio) {
        if(optPorfolio.isPresent()) return optPorfolio.get();
        else throw new EntityNotFoundException(id, Portfolio.class);
    } 
}
