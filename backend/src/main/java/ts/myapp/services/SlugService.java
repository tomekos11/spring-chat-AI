package ts.myapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ts.myapp.models.shares.ShareRepository;
import ts.myapp.models.users.UserRepository;

import java.util.Random;

@Service
public class SlugService {

    @Autowired
    private ShareRepository shareRepository;

    private final Random random = new Random();

    public String generateUniqueSlug(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder slugBuilder = new StringBuilder();

        while (true) {
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                slugBuilder.append(characters.charAt(index));
            }

            String slug = slugBuilder.toString();

            // Sprawdź, czy slug jest unikalny w bazie danych
            if (!shareRepository.existsBySlug(slug)) {
                return slug;
            }

            // Jeśli już istnieje, wyczyść builder i spróbuj ponownie
            slugBuilder.setLength(0);
        }
    }
}