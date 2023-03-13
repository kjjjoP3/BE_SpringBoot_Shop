package edu.poly.springshop.service;

import edu.poly.springshop.domain.Manufacturer;
import edu.poly.springshop.dto.ManufacturerDto;
import edu.poly.springshop.exception.ManufacturerException;
import edu.poly.springshop.repository.ManufacturerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Manufacturer insertManufacturer(ManufacturerDto dto){
        List<?> foundedList = manufacturerRepository.findByNameContainsIgnoreCase(dto.getName());

        if(foundedList.size() > 0){
                throw new ManufacturerException("Manufacturer name is existed");
        }

        Manufacturer entity = new Manufacturer();
        BeanUtils.copyProperties(dto, entity);

        if(dto.getLogoFile() != null){
                String filename = fileStorageService.storeLogoFile(dto.getLogoFile());
                entity.setLogo(filename);
                dto.setLogoFile(null);
        }

        return manufacturerRepository.save(entity);
    }

    public Manufacturer updateManufacturer(Long id, ManufacturerDto dto) {
        Optional<Manufacturer> manufacturerOptional = manufacturerRepository.findById(id);

        if (manufacturerOptional.isEmpty()) {
            throw new ManufacturerException("Manufacturer not found");
        }

        Manufacturer manufacturer = manufacturerOptional.get();
        manufacturer.setName(dto.getName());

        if (dto.getLogoFile() != null) {
            String filename = fileStorageService.storeLogoFile(dto.getLogoFile());
            manufacturer.setLogo(filename);
        }

        return manufacturerRepository.save(manufacturer);
    }





    public List<?> findAll(){
        return manufacturerRepository.findAll();
    }

    public Page<Manufacturer> findAll(Pageable pageable){
        return manufacturerRepository.findAll(pageable);
    }

    public Page<Manufacturer> findByName(String name,Pageable pageable){
        return manufacturerRepository.findByNameContainsIgnoreCase(name,pageable);
    }

    public Manufacturer findById(Long id){
        Optional<Manufacturer> found = manufacturerRepository.findById(id);

        if(found.isEmpty()){
                throw new ManufacturerException("Manufacturer with id " + id + "does not existed");
        }

        return found.get();
    }

    public void deleteById(Long id){
        Manufacturer existed = findById(id);

        manufacturerRepository.delete(existed);
    }



    // test
    public Manufacturer getManufacturerById(Long id) {
        Optional<Manufacturer> optional = manufacturerRepository.findById(id);
        return optional.orElse(null);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }


}
