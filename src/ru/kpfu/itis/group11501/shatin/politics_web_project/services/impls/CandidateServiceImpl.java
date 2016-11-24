package ru.kpfu.itis.group11501.shatin.politics_web_project.services.impls;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Election;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.User;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.CandidateRepository;
import ru.kpfu.itis.group11501.shatin.politics_web_project.repositories.impls.CandidateRepositoryImpl;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class CandidateServiceImpl implements ru.kpfu.itis.group11501.shatin.politics_web_project.services.CandidateService {
    CandidateRepository candidateRepository;
    public CandidateServiceImpl(){
        candidateRepository = new CandidateRepositoryImpl();
    }
    @Override
    public Candidate getCandidateForAgent(User agent) {
        return candidateRepository.getCandidateForAgent(agent);
    }

    @Override
    public Candidate getCandidateFromElectionById(Election election, Long candidateId) {
        for (Candidate candidate : election.getCandidates()){
            if (candidate.getId().equals(candidateId)){
                return candidate;
            }
        }
        return null;
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.getCandidateById(id);
    }

    @Override
    public boolean updateName(long id, String name) {
        return candidateRepository.updateName(id, name);
    }

    @Override
    public boolean updateId(long id, String newIdString) {
        try {
            long newId = Long.parseLong(newIdString);
            return candidateRepository.updateId(id, newId);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateInfo(long id, String info) {
        return candidateRepository.updateInfo(id, info);
    }

    @Override
    public boolean updateAchievements(long id, String achievements) {
        return candidateRepository.updateAchievements(id, achievements);
    }

    @Override
    public boolean updateElection_program(long id, String program) {
        return candidateRepository.updateElectionProgram(id, program);
    }

    @Override
    public boolean updateImage_src(long id, String src) {
        return  candidateRepository.updateImageSrc(id, src);
    }

    @Override
    public boolean updateSeats_in_parliament(long id, String seats) {
        try {
            int intSeats = Integer.parseInt(seats);
            return  candidateRepository.updateSeatsInParliament(id, intSeats);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return false;
    }

}
