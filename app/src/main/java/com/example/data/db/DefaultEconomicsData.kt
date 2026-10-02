package com.example.data.db

import com.example.data.model.AppSetting
import com.example.data.model.MaterialTopic
import com.example.data.model.QuizQuestion
import com.example.data.model.Student
import com.example.data.model.QuizResult
import com.example.data.model.GameScore
import com.example.data.model.ActivityLog

object DefaultEconomicsData {

    val initialSettings = listOf(
        AppSetting("teacher_username", "nursalim"),
        AppSetting("teacher_password", "123456"),
        AppSetting("teacher_name", "Nur Salim, S. Pd"),
        AppSetting("school_name", "SMA Negeri 1 Belitang II"),
        AppSetting("quiz_unlocked", "true"), // Izin guru kuis
        AppSetting("game_unlocked", "true")   // Izin guru game
    )

    val initialTopics = listOf(
        // BAB 1
        MaterialTopic(
            id = 1,
            chapterNumber = 1,
            chapterTitle = "Konsep Dasar Ilmu Ekonomi",
            topicNumber = 1,
            topicTitle = "Kelangkaan dan Kebutuhan Manusia",
            summary = "Memahami hakikat ilmu ekonomi, konsep kelangkaan (scarcity), faktor penyebab kelangkaan, serta klasifikasi kebutuhan manusia.",
            content = """
                A. Pengertian Ilmu Ekonomi
                Kata ekonomi berasal dari bahasa Yunani, yaitu Oikonomia yang terdiri dari kata Oikos (rumah tangga) dan Nomos (aturan/hukum). Secara sederhana, ekonomi diartikan sebagai aturan rumah tangga atau manajemen rumah tangga. Menurut Paul A. Samuelson, ilmu ekonomi adalah studi tentang bagaimana manusia dan masyarakat memilih memanfaatkan sumber daya langka untuk memproduksi berbagai komoditas dan mendistribusikannya untuk konsumsi sekarang maupun masa depan.

                B. Konsep Kelangkaan (Scarcity)
                Kelangkaan merupakan inti masalah ekonomi, yaitu kondisi di mana kebutuhan manusia yang tidak terbatas berhadapan dengan alat pemuas kebutuhan (barang dan jasa) yang jumlahnya terbatas.
                Faktor penyebab kelangkaan:
                1. Keterbatasan sumber daya alam dan lingkungan hidup.
                2. Keterbatasan kemampuan manusia dalam mengolah sumber daya (IPTEK).
                3. Pertambahan populasi manusia yang jauh lebih cepat daripada produksi alat pemuas.
                4. Terjadinya bencana alam dan kerusakan lingkungan.
                5. Pandemi dan krisis sosial global.

                C. Klasifikasi Kebutuhan Manusia
                1. Menurut Intensitas:
                   - Kebutuhan Primer: Kebutuhan mutlak yang harus dipenuhi (pangan, sandang, papan).
                   - Kebutuhan Sekunder: Kebutuhan pelengkap setelah primer terpenuhi (pendidikan, sepeda motor, alat elektronik).
                   - Kebutuhan Tersier: Kebutuhan barang mewah untuk kepuasan prestise (mobil sport, perhiasan mewah).
                2. Menurut Waktu Pemenuhan: Kebutuhan sekarang vs masa depan.
                3. Menurut Sifat: Kebutuhan jasmani vs rohani.
                4. Menurut Subjek: Kebutuhan individu vs kebutuhan kelompok/sosial.
            """.trimIndent(),
            videoTitle = "Konsep Kelangkaan & Kebutuhan Manusia - SMA N 1 Belitang II",
            videoUrl = "https://www.youtube.com/watch?v=1F2lXk_jQ8s",
            videoDuration = "12 Menit",
            keyTakeaways = "1. Kelangkaan timbul karena kebutuhan tak terbatas vs alat pemuas terbatas.\n2. Prioritaskan kebutuhan primer sebelum sekunder dan tersier."
        ),
        MaterialTopic(
            id = 2,
            chapterNumber = 1,
            chapterTitle = "Konsep Dasar Ilmu Ekonomi",
            topicNumber = 2,
            topicTitle = "Biaya Peluang (Opportunity Cost) & Skala Prioritas",
            summary = "Menganalisis pengorbanan terbaik yang hilang akibat pilihan ekonomi serta menyusun skala prioritas rasional.",
            content = """
                A. Biaya Peluang (Opportunity Cost)
                Biaya peluang adalah nilai kesempatan terbaik yang harus dikorbankan ketika memilih salah satu alternatif pilihan. Biaya peluang tidak selalu berbentuk uang tunai (eksplisit), melainkan keuntungan atau manfaat tertinggi dari opsi yang tidak diambil (implisit).
                
                Contoh:
                Seorang lulusan SMA memiliki dua tawaran: bekerja di pabrik dengan gaji Rp3.500.000/bulan atau kuliah penuh waktu di perguruan tinggi. Jika ia memutuskan untuk kuliah, maka biaya peluangnya adalah gaji Rp3.500.000/bulan yang tidak didapatkan selama masa studi.
                
                Ciri-ciri Biaya Peluang:
                - Selalu timbul karena keterbatasan sumber daya dan adanya banyak pilihan.
                - Nilai yang dihitung adalah nilai terbaik berikutnya (next best alternative), bukan penjumlahan seluruh pilihan yang ditinggalkan.
                
                B. Skala Prioritas Kebutuhan
                Skala prioritas adalah urutan daftar kebutuhan yang disusun berdasarkan tingkat kepentingan dan urgensinya.
                Tingkatan Prioritas:
                1. Prioritas I: Sangat mendesak dan sangat penting (kebutuhan pokok, biaya obat sakit).
                2. Prioritas II: Penting tetapi kurang mendesak (buku referensi semester depan).
                3. Prioritas III: Kurang penting dan tidak mendesak (rekreasi tambahan, aksesoris dekoratif).
            """.trimIndent(),
            videoTitle = "Memahami Biaya Peluang Secara Aplikatif - Kelas X",
            videoUrl = "https://www.youtube.com/watch?v=2K9kXj9k9Qw",
            videoDuration = "10 Menit",
            keyTakeaways = "1. Biaya peluang adalah nilai terbaik yang dikorbankan.\n2. Selalu gunakan skala prioritas untuk menghindari keputusan konsumtif."
        ),

        // BAB 2
        MaterialTopic(
            id = 3,
            chapterNumber = 2,
            chapterTitle = "Masalah Ekonomi & Sistem Ekonomi",
            topicNumber = 1,
            topicTitle = "Masalah Pokok Ekonomi Klasik dan Modern",
            summary = "Membedakan inti permasalahan ekonomi klasik (produksi, distribusi, konsumsi) dengan ekonomi modern (What, How, For Whom).",
            content = """
                A. Masalah Ekonomi Klasik
                Teori ekonomi klasik yang dipelopori Adam Smith merumuskan masalah ekonomi menjadi 3:
                1. Masalah Produksi: Menghasilkan barang dan jasa yang dibutuhkan masyarakat.
                2. Masalah Distribusi: Menyalurkan barang dari produsen sampai ke tangan konsumen dengan tepat waktu dan efisien.
                3. Masalah Konsumsi: Apakah barang yang sudah sampai benar-benar digunakan dan dikonsumsi sesuai nilai gunanya.

                B. Masalah Pokok Ekonomi Modern
                Dalam pandangan ekonomi modern, masalah pokok dirumuskan dalam 3 pertanyaan fundamental:
                1. What (Barang apa yang diproduksi dan berapa banyak?):
                   Menentukan alokasi sumber daya untuk jenis barang dan jumlahnya yang paling dibutuhkan masyarakat.
                2. How (Bagaimana cara memproduksi barang tersebut?):
                   Menentukan kombinasi teknologi, metode kerja, pemilihan bahan baku, apakah padat karya (labor intensive) atau padat modal (capital intensive).
                3. For Whom (Untuk siapa barang diproduksi?):
                   Menentukan target segmentasi pasar dan bagaimana hasil produksi didistribusikan secara adil ke lapisan masyarakat.
            """.trimIndent(),
            videoTitle = "Analisis Masalah Ekonomi Modern (What, How, For Whom)",
            videoUrl = "https://www.youtube.com/watch?v=3M3kXj_09wE",
            videoDuration = "14 Menit",
            keyTakeaways = "1. Ekonomi modern fokus pada What, How, dan For Whom.\n2. Efisiensi produksi dan keadilan distribusi adalah kunci stabilitas."
        ),
        MaterialTopic(
            id = 4,
            chapterNumber = 2,
            chapterTitle = "Masalah Ekonomi & Sistem Ekonomi",
            topicNumber = 2,
            topicTitle = "Sistem-Sistem Ekonomi Dunia",
            summary = "Karakteristik sistem ekonomi tradisional, komando/sosialis, pasar/kapitalis, dan sistem ekonomi Pancasila di Indonesia.",
            content = """
                A. Macam-Macam Sistem Ekonomi
                Sistem ekonomi adalah perpaduan dari aturan-aturan atau cara-cara yang merupakan satu kesatuan dan digunakan untuk mencapai tujuan dalam perekonomian.

                1. Sistem Ekonomi Tradisional:
                   - Berlandaskan adat istiadat dan kebiasaan turun-temurun.
                   - Teknologi sederhana, menggunakan sistem barter.
                   - Kelebihan: Hubungan kekeluargaan erat. Kelemahan: Produktivitas rendah dan pertumbuhan lambat.

                2. Sistem Ekonomi Komando (Sosialis/Terpusat):
                   - Segala keputusan ekonomi dipegang oleh pemerintah pusat.
                   - Kepemilikan individu atas faktor produksi dibatasi.
                   - Kelebihan: Pengendalian inflasi dan pengangguran mudah. Kelemahan: Menghambat inisiatif dan daya kreasi individu.

                3. Sistem Ekonomi Pasar (Kapitalis/Liberal):
                   - Mekanisme pasar (permintaan dan penawaran) menentukan seluruh kegiatan ekonomi.
                   - Motif mencari laba maksimum dan kebebasan memiliki faktor produksi.
                   - Kelebihan: Efisiensi tinggi, persaingan mendorong inovasi. Kelemahan: Ketimpangan ekonomi tinggi, rawan monopoli.

                4. Sistem Ekonomi Campuran & Sistem Demokrasi Ekonomi Pancasila (Indonesia):
                   - Menggabungkan kelebihan pasar dan regulasi pemerintah.
                   - Berdasarkan Pasal 33 UUD 1945: Perekonomian disusun atas asas kekeluargaan; cabang produksi penting dikuasai negara untuk kemakmuran rakyat.
            """.trimIndent(),
            videoTitle = "Komparasi Sistem Ekonomi Dunia & Ekonomi Pancasila",
            videoUrl = "https://www.youtube.com/watch?v=4N4kXj_88eR",
            videoDuration = "15 Menit",
            keyTakeaways = "1. Tidak ada sistem yang sempurna murni.\n2. Indonesia menerapkan Sistem Ekonomi Pancasila berlandaskan Pasal 33 UUD 1945."
        ),

        // BAB 3
        MaterialTopic(
            id = 5,
            chapterNumber = 3,
            chapterTitle = "Pelaku Ekonomi & Kegiatan Ekonomi",
            topicNumber = 1,
            topicTitle = "Kegiatan Produksi, Distribusi, dan Konsumsi",
            summary = "Mempelajari faktor produksi, konsep nilai guna (utilitas), serta rantai distribusi dalam alur perekonomian.",
            content = """
                A. Kegiatan Produksi
                Produksi adalah kegiatan menambah nilai guna suatu benda atau menciptakan benda baru agar lebih bermanfaat memenuhi kebutuhan.
                Faktor Produksi meliputi:
                1. Faktor Alam (tanah, air, iklim, bahan tambang) - faktor asli.
                2. Faktor Tenaga Kerja (terdidik, terlatih, tidak terdidik/terlatih) - faktor asli.
                3. Faktor Modal (mesin, gedung, dana investasi) - faktor turunan.
                4. Faktor Kewirausahaan (Entrepreneurship) - faktor turunan pengorganisasi.

                B. Kegiatan Distribusi
                Menyalurkan produk dari produsen ke konsumen.
                - Distribusi Langsung: Produsen langsung ke konsumen akhir tanpa perantara.
                - Distribusi Semi Langsung: Melalui saluran milik produsen sendiri (toko cabang).
                - Distribusi Tidak Langsung: Melalui mata rantai pedagang besar (grosir), agen, dan pengecer.

                C. Kegiatan Konsumsi
                Kegiatan menghabiskan atau mengurangi nilai guna barang dan jasa secara berangsur-angsur maupun sekaligus.
                Teori Perilaku Konsumen:
                - Pendekatan Kardinal (Hukum Gossen I dan II): Kepuasan dapat diukur dengan satuan utilitas.
                - Pendekatan Ordinal (Kurva Indiferensi): Kepuasan tidak diukur dengan angka mutlak, melainkan diurutkan melalui preferensi.
            """.trimIndent(),
            videoTitle = "Kegiatan Ekonomi dan Faktor-Faktor Produksi - Nur Salim, S. Pd",
            videoUrl = "https://www.youtube.com/watch?v=5O5kXj_99eT",
            videoDuration = "13 Menit",
            keyTakeaways = "1. Faktor produksi terdiri dari alam, tenaga kerja, modal, dan kewirausahaan.\n2. Konsumen rasional memaksimalkan kepuasan marginal per biaya."
        ),
        MaterialTopic(
            id = 6,
            chapterNumber = 3,
            chapterTitle = "Pelaku Ekonomi & Kegiatan Ekonomi",
            topicNumber = 2,
            topicTitle = "Circular Flow Diagram (Arus Lingkar Kegiatan Ekonomi)",
            summary = "Interaksi 4 pelaku ekonomi: RTK, RTP, RTG (Pemerintah), dan Masyarakat Luar Negeri dalam pasar faktor dan pasar output.",
            content = """
                A. Empat Pelaku Ekonomi Utama
                1. Rumah Tangga Konsumen (RTK): Pemilik faktor produksi (tanah, tenaga, modal) dan konsumen barang/jasa.
                2. Rumah Tangga Produsen (RTP): Pengguna faktor produksi dan penghasil barang/jasa.
                3. Rumah Tangga Pemerintah (RTG): Pengatur, regulator, produsen barang publik, dan pemungut pajak.
                4. Masyarakat Luar Negeri (RTLN): Pelaku perdagangan internasional melalui kegiatan ekspor dan impor.

                B. Diagram Alir Melingkar (Circular Flow Diagram)
                - Perekonomian 2 Sektor (Sederhana): RTK dan RTP.
                  * Pasar Faktor: RTK menyerahkan faktor produksi ke RTP; RTP memberikan imbalan (sewa, upah, bunga, laba).
                  * Pasar Barang/Output: RTP menjual produk ke RTK; RTK melakukan pembelanjaan uang.
                - Perekonomian 3 Sektor (Tertutup): Ditambah Pemerintah (RTG). Pemerintah memungut pajak dari RTK dan RTP, serta memberikan subsidi dan fasilitas publik.
                - Perekonomian 4 Sektor (Terbuka): Ditambah Masyarakat Luar Negeri melalui arus devisa ekspor dan impor barang serta modal antarnegara.
            """.trimIndent(),
            videoTitle = "Visualisasi Interaktif Circular Flow Diagram 4 Sektor",
            videoUrl = "https://www.youtube.com/watch?v=6P6kXj_11eY",
            videoDuration = "14 Menit",
            keyTakeaways = "1. RTK menerima imbalan atas penyediaan faktor produksi.\n2. Perekonomian terbuka melibatkan pasar internasional melalui ekspor dan impor."
        ),

        // BAB 4
        MaterialTopic(
            id = 7,
            chapterNumber = 4,
            chapterTitle = "Pasar & Terbentuknya Harga Pasar",
            topicNumber = 1,
            topicTitle = "Permintaan, Penawaran, dan Keseimbangan Pasar",
            summary = "Hukum permintaan dan penawaran, pergeseran kurva, serta titik ekuilibrium harga dan kuantitas pasar.",
            content = """
                A. Teori Permintaan (Demand)
                Permintaan adalah sejumlah barang/jasa yang diinginkan dan mampu dibeli konsumen pada berbagai tingkat harga dalam waktu tertentu.
                - Hukum Permintaan: "Jika harga suatu barang naik, maka jumlah barang yang diminta akan turun, dan sebaliknya (Ceteris Paribus)."
                - Faktor yang mempengaruhi: Harga barang itu sendiri, pendapatan masyarakat, selera konsumen, harga barang substitusi dan komplementer, perkiraan harga masa depan.

                B. Teori Penawaran (Supply)
                Penawaran adalah sejumlah barang/jasa yang ditawarkan produsen pada berbagai tingkat harga dan waktu.
                - Hukum Penawaran: "Jika harga suatu barang naik, maka jumlah barang yang ditawarkan bertambah, dan sebaliknya (Ceteris Paribus)."
                - Faktor yang mempengaruhi: Biaya produksi, kemajuan teknologi, jumlah produsen di pasar, pajak dan subsidi pemerintah.

                C. Keseimbangan Pasar (Equilibrium)
                Keseimbangan pasar tercapai saat jumlah yang diminta sama dengan jumlah yang ditawarkan pada harga tertentu (Qd = Qs).
                - Titik temu antara kurva permintaan dan kurva penawaran dinamakan Titik Ekuilibrium (E).
                - Jika harga di atas ekuilibrium, terjadi Surplus (kelebihan penawaran).
                - Jika harga di bawah ekuilibrium, terjadi Shortage (kelebihan permintaan).
            """.trimIndent(),
            videoTitle = "Membaca Kurva Permintaan, Penawaran & Titik Ekuilibrium",
            videoUrl = "https://www.youtube.com/watch?v=7Q7kXj_22eU",
            videoDuration = "15 Menit",
            keyTakeaways = "1. Hukum permintaan berbanding terbalik, hukum penawaran berbanding lurus.\n2. Harga ekuilibrium terjadi ketika Qd = Qs."
        ),
        MaterialTopic(
            id = 8,
            chapterNumber = 4,
            chapterTitle = "Pasar & Terbentuknya Harga Pasar",
            topicNumber = 2,
            topicTitle = "Elastisitas dan Struktur Pasar",
            summary = "Menghitung koefisien elastisitas harga serta karakteristik pasar persaingan sempurna, monopoli, oligopoli, dan monopolistik.",
            content = """
                A. Konsep Elastisitas Harga
                Elastisitas mengukur derajat kepekaan perubahan jumlah barang yang diminta atau ditawarkan akibat perubahan harga barang tersebut.
                Rumus: Ed = (% Perubahan Jumlah Barang) / (% Perubahan Harga)
                Kriteria Elastisitas:
                1. Elastis (Ed > 1): Perubahan harga kecil menyebabkan perubahan jumlah yang besar (barang mewah).
                2. Inelastis (Ed < 1): Perubahan harga tidak banyak mempengaruhi jumlah (barang pokok seperti beras).
                3. Elastis Uniter (Ed = 1): Persentase perubahan jumlah sebanding dengan persentase perubahan harga.
                4. Elastis Sempurna (Ed = tak terhingga): Garis kurva horizontal.
                5. Inelastis Sempurna (Ed = 0): Garis kurva vertikal (misal obat penyelamat nyawa).

                B. Struktur Pasar
                1. Pasar Persaingan Sempurna:
                   - Banyak penjual dan pembeli.
                   - Produk bersifat homogen (seragam).
                   - Penjual adalah price taker (pengambil harga). Bebas keluar masuk pasar.
                2. Pasar Persaingan Tidak Sempurna:
                   - Pasar Monopoli: Hanya ada satu penjual yang menguasai pasar (misal PLN untuk listrik). Penjual adalah price maker.
                   - Pasar Oligopoli: Dikuasai oleh beberapa produsen besar (misal industri semen, telekomunikasi seluler). Rawan perang harga.
                   - Pasar Monopolistik: Banyak produsen tetapi produk memiliki diferensiasi merk dan kualitas (misal sabun, pasta gigi).
            """.trimIndent(),
            videoTitle = "Elastisitas Harga dan Struktur Pasar di Indonesia",
            videoUrl = "https://www.youtube.com/watch?v=8R8kXj_33eI",
            videoDuration = "16 Menit",
            keyTakeaways = "1. Barang kebutuhan pokok cenderung bersifat inelastis.\n2. Pasar persaingan sempurna menghasilkan efisiensi tertinggi, monopoli memiliki daya tawar terbesar."
        )
    )

    // 10 KUIS PILIHAN GANDA UNTUK SETIAP POKOK MATERI (Total 8 Pokok Materi x 10 Soal = 80 Soal)
    val initialQuestions: List<QuizQuestion> = listOf(
        // POKOK 1 (TopicId = 1: Kelangkaan & Kebutuhan)
        QuizQuestion(0, 1, "Secara etimologis, kata ekonomi berasal dari bahasa Yunani 'Oikonomia' yang tersusun dari kata 'Oikos' dan 'Nomos', yang memiliki arti...", "Rumah tangga dan aturan/hukum", "Kekayaan dan perniagaan", "Uang dan kekuasaan", "Masyarakat dan pasar", 0, "Oikos berarti rumah tangga dan Nomos berarti aturan atau tata hukum manajemen."),
        QuizQuestion(0, 1, "Inti masalah ekonomi yang mendasari munculnya ilmu ekonomi di dunia adalah...", "Kurangnya uang tunai di masyarakat", "Ketidakseimbangan antara kebutuhan manusia yang tak terbatas dengan alat pemuas yang terbatas", "Kekurangan jumlah tenaga kerja terampil", "Adanya persaingan dagang internasional yang tidak sehat", 1, "Kelangkaan (scarcity) muncul karena kebutuhan manusia tidak terbatas sementara alat pemuasnya terbatas."),
        QuizQuestion(0, 1, "Berikut ini faktor yang BUKAN merupakan penyebab kelangkaan sumber daya adalah...", "Keterbatasan daya dukung alam", "Bencana alam dan pemanasan global", "Penemuan teknologi baru yang ramah lingkungan dan hemat energi", "Pertumbuhan populasi yang lebih cepat dibanding produksi", 2, "Penemuan teknologi baru yang efisien justru membantu mengatasi kelangkaan."),
        QuizQuestion(0, 1, "Makanan, pakaian, dan tempat tinggal termasuk dalam klasifikasi kebutuhan...", "Sekunder", "Tersier", "Primer", "Masa depan", 2, "Kebutuhan primer adalah kebutuhan pokok mutlak demi kelangsungan hidup manusia."),
        QuizQuestion(0, 1, "Berdasarkan waktunya, menabung untuk biaya pendidikan perguruan tinggi 3 tahun ke depan merupakan kebutuhan...", "Jasmani", "Sekarang", "Masa depan", "Mendesak", 2, "Kebutuhan masa depan adalah pemenuhan kebutuhan yang direncanakan untuk waktu yang akan datang."),
        QuizQuestion(0, 1, "Barang bebas dan barang ekonomi dibedakan berdasarkan...", "Cara memperolehnya dan pengorbanan yang diperlukan", "Tingkat ketahanan barang saat dipakai", "Hubungannya dengan barang lain di pasar", "Tujuan akhir penggunaannya", 0, "Barang bebas diperoleh cuma-cuma tanpa pengorbanan (misal udara), barang ekonomi memerlukan pengorbanan."),
        QuizQuestion(0, 1, "Payung memiliki nilai guna yang meningkat ketika musim hujan tiba. Hal ini merupakan contoh dari nilai guna bentuk...", "Place utility (kegunaan tempat)", "Time utility (kegunaan waktu)", "Form utility (kegunaan bentuk)", "Ownership utility (kegunaan milik)", 1, "Time utility adalah peningkatan kegunaan barang karena perubahan waktu penggunaan."),
        QuizQuestion(0, 1, "Kondisi kelangkaan air bersih yang terjadi di beberapa daerah saat musim kemarau panjang mendorong warga untuk...", "Membeli barang tersier lebih banyak", "Menyusun skala prioritas penghematan air", "Menambah konsumsi kebutuhan sekunder", "Mengabaikan kebutuhan sanitasi keluarga", 1, "Skala prioritas membantu manusia mengalokasikan sumber daya yang langka secara tepat."),
        QuizQuestion(0, 1, "Ibu Ani lebih mendahulukan membeli beras dan obat untuk anaknya yang sakit sebelum membeli tas pesta baru. Sikap Ibu Ani mencerminkan...", "Prinsip ekonomi dan pemenuhan skala prioritas rasional", "Tindakan spekulasi ekonomi", "Kecenderungan konsumsi berlebihan", "Sikap berhemat yang merugikan", 0, "Mendahulukan kebutuhan mendesak dan pokok merupakan bentuk penerapan skala prioritas rasional."),
        QuizQuestion(0, 1, "Di bawah ini yang termasuk contoh barang komplementer (saling melengkapi) adalah...", "Beras dengan jagung", "Kopi dengan teh", "Mobil dengan bahan bakar bensin", "Sepatu dengan sandal jepit", 2, "Barang komplementer adalah barang yang kegunaannya optimal jika digunakan bersama-sama."),

        // POKOK 2 (TopicId = 2: Biaya Peluang & Skala Prioritas)
        QuizQuestion(0, 2, "Nilai kesempatan terbaik berikutnya yang harus dikorbankan karena memilih suatu alternatif keputusan dinamakan...", "Biaya produksi", "Biaya peluang (opportunity cost)", "Biaya operasional", "Biaya marginal", 1, "Biaya peluang adalah nilai manfaat tertinggi dari alternatif yang tidak dipilih."),
        QuizQuestion(0, 2, "Randi memiliki modal Rp10.000.000. Jika ia membuka usaha sablon ia untung Rp2.000.000/bln, jika jualan online untung Rp2.500.000/bln, dan jika warung kopi untung Rp1.800.000/bln. Jika Randi memilih membuka usaha sablon, berapa biaya peluangnya?", "Rp1.800.000", "Rp2.000.000", "Rp2.500.000", "Rp4.300.000", 2, "Biaya peluang dihitung dari alternatif terbaik yang dikorbankan, yaitu usaha jualan online sebesar Rp2.500.000."),
        QuizQuestion(0, 2, "Berbeda dengan biaya eksplisit yang dikeluarkan tunai, biaya peluang umumnya bersifat...", "Implisit berupa hilangnya potensi keuntungan", "Wajib dicatat dalam kwitansi kasir", "Pasti bernilai negatif di neraca", "Selalu dapat dihindari dengan mudah", 0, "Biaya peluang mencakup manfaat implisit yang hilang."),
        QuizQuestion(0, 2, "Dalam menyusun skala prioritas, hal yang menjadi pertimbangan utama seorang pelajar adalah...", "Gengsi dan mode tren terkini di media sosial", "Tingkat urgensi pemenuhan dan manfaat jangka panjang pendidikan", "Harga barang yang paling mahal di toko", "Pendapat teman sebaya mengenai barang tersebut", 1, "Pelajar harus memprioritaskan kebutuhan yang paling mendesak dan bermanfaat bagi pendidikannya."),
        QuizQuestion(0, 2, "Seorang petani memutuskan menanam jagung di lahannya. Biaya peluang dari keputusan tersebut adalah...", "Hasil panen padi yang batal diperoleh dari lahan tersebut", "Biaya membeli bibit jagung", "Biaya menyiram tanaman jagung setiap pagi", "Keuntungan dari hasil penjualan jagung di pasar", 0, "Manfaat panen alternatif terbaik yang hilang (padi) adalah biaya peluangnya."),
        QuizQuestion(0, 2, "Kelangkaan sumber daya memaksa manusia untuk selalu bertindak...", "Konsumtif dan hedonis", "Membuat pilihan rasional dengan mempertimbangkan trade-off", "Menyerahkan semua keputusan kepada pemerintah", "Mengabaikan kebutuhan masa depan", 1, "Kelangkaan mengharuskan pilihan rasional dan mempertimbangkan trade-off (untung-rugi pilihan)."),
        QuizQuestion(0, 2, "Faktor utama yang membedakan skala prioritas setiap individu adalah...", "Warna kulit dan suku bangsa", "Pendapatan, tingkat kebutuhan, dan lingkungan sosial", "Jumlah saldo bank yang sama persis", "Kewajiban membayar pajak bumi dan bangunan", 1, "Skala prioritas dipengaruhi oleh pendapatan, pekerjaan, dan kondisi lingkungan hidup."),
        QuizQuestion(0, 2, "Tindakan mendahulukan rekreasi ke luar kota daripada melunasi iuran sekolah yang menunggak merupakan contoh...", "Penyusunan skala prioritas yang keliru dan tidak rasional", "Penerapan prinsip efisiensi ekonomi", "Investasi modal jangka panjang", "Peningkatan produktivitas belajar", 0, "Melupakan kewajiban mendesak demi kesenangan sesaat adalah keputusan ekonomi tidak rasional."),
        QuizQuestion(0, 2, "Jika biaya peluang dari melanjutkan kuliah dirasa terlalu tinggi bagi seseorang yang memiliki tawaran gaji besar, maka keputusannya...", "Pasti salah menurut ilmu ekonomi", "Rasional berdasarkan analisis biaya manfaat pribadinya", "Tidak diperbolehkan menurut aturan ketenagakerjaan", "Akan merugikan perekonomian negara", 1, "Setiap orang menimbang manfaat dan biaya peluang sesuai situasi pribadinya secara rasional."),
        QuizQuestion(0, 2, "Kebutuhan yang bila tidak dipenuhi tidak mengancam kelangsungan hidup manusia tetapi meningkatkan status sosial dinamakan kebutuhan...", "Primer", "Vital", "Tersier", "Jasmani", 2, "Kebutuhan tersier atau barang mewah berfungsi meningkatkan prestise atau status sosial."),

        // POKOK 3 (TopicId = 3: Masalah Ekonomi Klasik vs Modern)
        QuizQuestion(0, 3, "Menurut teori ekonomi klasik, tiga masalah pokok ekonomi yang dihadapi manusia adalah...", "What, How, For Whom", "Produksi, Distribusi, Konsumsi", "Inflasi, Pengangguran, Kemiskinan", "Modal, Tenaga Kerja, Kewirausahaan", 1, "Masalah ekonomi klasik berfokus pada Produksi, Distribusi, dan Konsumsi."),
        QuizQuestion(0, 3, "Seorang produsen roti bingung memilih apakah adonan akan diaduk dengan mesin pabrik otomatis atau tenaga manual warga desa. Masalah ini berkaitan dengan...", "What", "How", "For Whom", "When", 1, "Masalah 'How' berkaitan dengan teknik dan teknologi kombinasi faktor produksi yang efisien."),
        QuizQuestion(0, 3, "Sebuah perusahaan tekstil memutuskan untuk memproduksi seragam sekolah berbahan katun sejuk sebanyak 50.000 stel untuk tahun ajaran baru. Keputusan ini memecahkan masalah...", "How", "For Whom", "What", "Where", 2, "Menentukan jenis produk dan jumlah unit yang diproduksi adalah pemecahan masalah 'What'."),
        QuizQuestion(0, 3, "Pertanyaan 'For Whom' dalam masalah ekonomi modern berfokus pada persoalan...", "Berapa lama barang dapat disimpan di gudang", "Bagaimana produk didistribusikan kepada segmen konsumen yang tepat dan adil", "Siapa yang mendanai modal awal perusahaan", "Bahan baku apa yang paling murah didapat", 1, "'For Whom' menjawab target pasar dan pemerataan distribusi barang hasil produksi."),
        QuizQuestion(0, 3, "Masalah distribusi dalam teori ekonomi klasik dianggap berhasil jika...", "Barang produksi tersimpan rapi selamanya di pabrik", "Barang sampai ke tangan konsumen tepat waktu, jumlah, dan kualitas", "Harga barang dinaikkan setinggi mungkin", "Produsen tidak perlu mengeluarkan biaya transportasi", 1, "Distribusi yang berhasil menjamin kelancaran arus barang dari produsen ke konsumen."),
        QuizQuestion(0, 3, "Penerapan metode padat karya (labor intensive) pada industri rokok bertujuan untuk...", "Mengurangi ketergantungan pada listrik pabrik", "Menyerap banyak tenaga kerja lokal meskipun produktivitasnya bersaing", "Meniadakan persaingan dengan pabrik luar negeri", "Membuat harga jual menjadi paling mahal", 1, "Metode padat karya mempekerjakan banyak sumber daya manusia untuk memecahkan masalah 'How'."),
        QuizQuestion(0, 3, "Masalah ekonomi timbul karena sifat kebutuhan manusia yang...", "Mudah dipuaskan dan terbatas", "Terbatas dan selalu berkurang", "Tidak terbatas dan terus berkembang", "Sama persis antara satu individu dengan lainnya", 2, "Kebutuhan manusia tidak pernah berhenti dan terus bertambah seiring peradaban."),
        QuizQuestion(0, 3, "Pemerintah menetapkan subsidi pupuk bagi petani padi kecil di pedesaan. Kebijakan ini berkaitan erat dengan solusi masalah...", "What saja", "How saja", "For Whom untuk menjamin ketahanan pangan lapisan masyarakat menengah ke bawah", "When saja", 2, "Distribusi bantuan dan hasil panen untuk golongan masyarakat yang membutuhkan menjawab 'For Whom'."),
        QuizQuestion(0, 3, "Suatu daerah surplus sayur-mayur tetapi penduduknya kekurangan ikan laut. Fenomena ini menunjukkan pentingnya mengatasi masalah ekonomi...", "Produksi", "Distribusi antarwilayah", "Konsumsi barang mewah", "Eksplorasi tambang", 1, "Perbedaan potensi geografis menuntut kelancaran distribusi antardaerah."),
        QuizQuestion(0, 3, "Dalam era revolusi industri 4.0, banyak perusahaan beralih ke kecerdasan buatan (AI) untuk pelayanan konsumen. Hal ini merupakan inovasi atas masalah...", "What", "How", "For Whom", "Where", 1, "Pemilihan teknologi baru dalam proses layanan adalah pemecahan masalah 'How'."),

        // POKOK 4 (TopicId = 4: Sistem Ekonomi Dunia & Pancasila)
        QuizQuestion(0, 4, "Sistem ekonomi di mana seluruh keputusan produksi, distribusi, dan kepemilikan aset diatur oleh pemerintah pusat dinamakan...", "Sistem Ekonomi Pasar", "Sistem Ekonomi Komando / Terpusat", "Sistem Ekonomi Tradisional", "Sistem Ekonomi Campuran", 1, "Sistem komando (sosialis) menempatkan negara sebagai pemegang kendali penuh perekonomian."),
        QuizQuestion(0, 4, "Ciri utama dari sistem ekonomi pasar (kapitalis/liberal) yang dikemukakan Adam Smith adalah...", "Adanya kebebasan individu memiliki modal dan bekerjanya mekanisme pasar", "Pemerintah menetapkan harga eceran tertinggi untuk semua barang", "Tidak adanya persaingan antar pengusaha", "Semua warga mendapatkan jatah upah yang sama rata", 0, "Sistem pasar mengandalkan kebebasan kepemilikan dan mekanisme 'invisible hand'."),
        QuizQuestion(0, 4, "Salah satu kelemahan nyata dari sistem ekonomi komando adalah...", "Sering terjadi monopoli oleh konglomerat swasta", "Mematikan inisiatif, kreativitas, dan daya kreasi warga negara", "Tingkat pengangguran sangat bergejolak bebas", "Pemerintah tidak bisa mengontrol inflasi sama sekali", 1, "Sentralisasi kekuasaan ekonomi cenderung menekan inisiatif dan inovasi masyarakat."),
        QuizQuestion(0, 4, "Sistem perekonomian yang berlaku di Indonesia berakar pada konstitusi UUD 1945 Pasal 33 yang berasaskan...", "Individualisme bebas", "Kekeluargaan dan gotong royong", "Monopoli negara mutlak", "Komersialisasi tanpa batas", 1, "Pasal 33 ayat (1) menyatakan perekonomian disusun sebagai usaha bersama berdasar asas kekeluargaan."),
        QuizQuestion(0, 4, "Cabang-cabang produksi yang penting bagi negara dan menguasai hajat hidup orang banyak di Indonesia dikuasai oleh...", "Pengusaha asing pemodal besar", "Negara dan dipergunakan untuk sebesar-besar kemakmuran rakyat", "Keluarga bangsawan terkemuka", "Organisasi pasar bebas internasional", 1, "Hal ini tercantum secara tegas dalam Pasal 33 ayat (2) UUD 1945."),
        QuizQuestion(0, 4, "Dalam sistem ekonomi tradisional, motif ekonomi yang melandasi kegiatan masyarakat adalah...", "Mencari laba sebesar-besarnya di bursa efek", "Memenuhi kebutuhan hidup sehari-hari secara sederhana", "Membangun pabrik industri berskala global", "Menimbun kekayaan berupa valuta asing", 1, "Sistem tradisional berfokus pada pemenuhan kebutuhan subsisten harian."),
        QuizQuestion(0, 4, "Kelebihan sistem ekonomi pasar yang memacu perkembangan teknologi secara pesat adalah...", "Adanya jaminan dari pemerintah agar usaha tidak pernah rugi", "Persaingan bebas mendorong efisiensi dan inovasi produk bermutu tinggi", "Penetapan harga yang seragam oleh serikat buruh", "Pemberian subsidi tanpa batas kepada importir", 1, "Persaingan ketat menuntut efisiensi biaya dan inovasi tiada henti."),
        QuizQuestion(0, 4, "Pada sistem ekonomi campuran, peran pemerintah ditujukan untuk...", "Mengambil alih seluruh kios pedagang pasar kecil", "Mencegah praktik monopoli merugikan dan menjaga stabilitas ekonomi nasional", "Melarang swasta menanamkan modal di dalam negeri", "Menghapus pemungutan pajak bagi konglomerat", 1, "Pemerintah mengoreksi kegagalan pasar (market failure) dan melindungi masyarakat lemah."),
        QuizQuestion(0, 4, "Ciri negatif demokrasi ekonomi yang harus dihindari di Indonesia menurut tap MPR adalah 'Free fight liberalism', yaitu...", "Kebebasan berusaha yang menimbulkan penindasan dan eksploitasi kaum kuat atas yang lemah", "Kerja sama mutualistik antar koperasi nelayan", "Pemungutan pajak progresif untuk keadilan sosial", "Pemberdayaan UMKM oleh perbankan nasional", 0, "Free fight liberalism menciptakan eksploitasi dan ketimpangan yang lebar."),
        QuizQuestion(0, 4, "Sistem ekonomi yang dianut sebagian besar negara berkembang di dunia saat ini pada dasarnya adalah...", "Komando murni tanpa kompromi", "Sistem ekonomi campuran dengan derajat intervensi berbeda-beda", "Tradisional murni tanpa mata uang", "Anarki perdagangan", 1, "Sebagian besar negara modern menerapkan sistem campuran antara mekanisme pasar dan regulasi negara."),

        // POKOK 5 (TopicId = 5: Kegiatan Produksi, Distribusi, Konsumsi)
        QuizQuestion(0, 5, "Segala kegiatan yang dilakukan untuk menambah faedah atau menciptakan barang dan jasa baru dinamakan...", "Konsumsi", "Produksi", "Investasi pasif", "Depresiasi", 1, "Produksi menambah nilai guna (utility) barang dan jasa."),
        QuizQuestion(0, 5, "Di bawah ini yang merupakan faktor produksi asli adalah...", "Modal dan Kewirausahaan", "Tanah (Alam) dan Tenaga Kerja", "Mesin pabrik dan Surat Berharga", "Gedung kantor dan Komputer", 1, "Faktor produksi asli bersumber dari anugerah alam dan tenaga manusia."),
        QuizQuestion(0, 5, "Seorang dokter bedah spesialis termasuk dalam kategori tenaga kerja...", "Terdidik", "Terlatih", "Tidak terdidik dan tidak terlatih", "Sukarela", 0, "Profesi dokter memerlukan pendidikan formal tinggi spesifik."),
        QuizQuestion(0, 5, "Penyaluran hasil kerajinan gerabah langsung dari tangan pengrajin kepada wisatawan yang datang ke studionya disebut...", "Distribusi tidak langsung", "Distribusi langsung", "Distribusi semi intensif", "Distribusi grosir eksklusif", 1, "Distribusi langsung tidak melalui perantara pihak ketiga."),
        QuizQuestion(0, 5, "Hukum pertambahan hasil yang semakin menurun (The Law of Diminishing Returns) dikemukakan oleh...", "David Ricardo", "Karl Marx", "John Maynard Keynes", "Milton Friedman", 0, "David Ricardo merumuskan The Law of Diminishing Returns pada faktor produksi."),
        QuizQuestion(0, 5, "Teori yang menyatakan bahwa 'bila pemenuhan suatu kebutuhan akan suatu barang dilakukan secara terus-menerus, maka kenikmatannya semakin menurun' adalah...", "Hukum Engel", "Hukum Gossen I", "Hukum Gossen II", "Hukum Penawaran", 1, "Hukum Gossen I menjelaskan penurunan utilitas marginal atas konsumsi kontinu."),
        QuizQuestion(0, 5, "Konsumen bertindak rasional apabila...", "Membeli barang karena melihat iklan selebritas favorit", "Membandingkan manfaat dan harga barang guna memaksimalkan kepuasan dalam batas anggarannya", "Menghabiskan seluruh tabungannya dalam satu hari belanja", "Menimbun barang sebanyak mungkin tanpa memakainya", 1, "Konsumen rasional mengoptimalkan kepuasan (utility) berdasarkan anggaran yang dimiliki."),
        QuizQuestion(0, 5, "Balas jasa yang diterima oleh pemilik faktor produksi modal berupa dana pinjaman investasi adalah...", "Upah (wage)", "Sewa (rent)", "Bunga modal (interest)", "Laba usaha (profit)", 2, "Imbalan untuk modal adalah bunga modal (interest)."),
        QuizQuestion(0, 5, "Perusahaan pengiriman paket kilat (logistik) berperan dalam kegiatan ekonomi pada sektor...", "Ekstraktif", "Agraris", "Distribusi dan Jasa", "Industri primer", 2, "Perusahaan logistik berperan menyalurkan barang dan menyediakan jasa distribusi."),
        QuizQuestion(0, 5, "Jika seorang konsumen mencapai titik kepuasan maksimum dalam mengkonsumsi barang X, maka nilai Marginal Utility (MU)-nya adalah...", "Tak terhingga positif", "Sama dengan nol", "Sangat negatif", "Sama dengan harga barang X dikali dua", 1, "Kepuasan total maksimum tercapai ketika marginal utility bernilai nol."),

        // POKOK 6 (TopicId = 6: Circular Flow Diagram)
        QuizQuestion(0, 6, "Dalam perekonomian dua sektor, pihak yang berinteraksi dalam pasar adalah...", "Rumah Tangga Konsumen (RTK) dan Rumah Tangga Produsen (RTP)", "Pemerintah dan Bank Indonesia", "Eksportir dan Importir", "Koperasi dan BUMN", 0, "Arus lingkar sederhana (2 sektor) melibatkan RTK dan RTP."),
        QuizQuestion(0, 6, "Rumah Tangga Konsumen (RTK) berperan di pasar faktor produksi sebagai...", "Penyedia/penjual faktor produksi tanah, tenaga kerja, modal", "Pembeli barang kebutuhan akhir", "Pemungut pajak pendapatan", "Pencetak mata uang rupiah", 0, "RTK adalah pemilik sekaligus penyedia faktor-faktor produksi."),
        QuizQuestion(0, 6, "Aliran imbalan yang diberikan Rumah Tangga Produsen (RTP) kepada RTK atas penggunaan tenaga kerja adalah...", "Sewa (Rent)", "Upah atau Gaji (Wage)", "Bunga (Interest)", "Dividen", 1, "Balas jasa atas tenaga kerja adalah upah/gaji."),
        QuizQuestion(0, 6, "Peran utama Rumah Tangga Pemerintah (RTG) dalam perekonomian tiga sektor adalah...", "Menjual seluruh cadangan minyak bumi ke luar negeri", "Sebagai regulator, penarik pajak, dan penyedia barang publik bagi masyarakat", "Membeli semua pabrik swasta yang bangkrut", "Menetapkan upah buruh menjadi sama di seluruh dunia", 1, "Pemerintah bertindak sebagai pengatur ekonomi, pemungut pajak, dan penyedia fasilitas publik."),
        QuizQuestion(0, 6, "Dalam diagram arus lingkar empat sektor, arus uang yang masuk ke dalam negeri karena penjualan barang ke luar negeri berasal dari transaksi...", "Impor", "Ekspor", "Pinjaman luar negeri komersial", "Subsidi perdagangan bebas", 1, "Ekspor mendatangkan devisa (arus kas masuk ke dalam negeri)."),
        QuizQuestion(0, 6, "Pajak yang dibayarkan oleh RTK dan RTP kepada pemerintah akan dikembalikan lagi kepada masyarakat dalam bentuk...", "Dividen saham swasta", "Pembangunan infrastruktur jalan, subsidi, dan fasilitas pelayanan umum", "Kenaikan harga barang eceran", "Bonus akhir tahun anggota DPR semata", 1, "Penerimaan pajak dialokasikan kembali untuk belanja publik dan subsidi."),
        QuizQuestion(0, 6, "Pasar output dalam circular flow diagram mempertemukan...", "Pencari kerja dengan perusahaan tambang", "Produsen yang menjual barang jadi dengan konsumen yang membeli", "Pemilik tanah sawah dengan penyewa", "Pemerintah dengan bank dunia", 1, "Pasar output adalah pasar barang dan jasa siap pakai."),
        QuizQuestion(0, 6, "Jika impor suatu negara lebih besar daripada ekspornya, maka neraca perdagangannya mengalami...", "Surplus", "Defisit", "Keseimbangan absolut", "Peningkatan cadangan devisa", 1, "Defisit neraca perdagangan terjadi jika impor melampaui ekspor."),
        QuizQuestion(0, 6, "Pengiriman tenaga kerja Indonesia (TKI) ke luar negeri dalam arus lingkar ekonomi tergolong ekspor...", "Barang modal", "Faktor produksi jasa tenaga kerja", "Barang komplementer", "Bahan mentah tambang", 1, "TKI adalah ekspor faktor produksi tenaga kerja ke luar negeri."),
        QuizQuestion(0, 6, "Perusahaan membayar pajak penghasilan badan (PPh Badan) kepada pemerintah. Ini merupakan aliran dana dari...", "RTK ke RTP", "RTP ke Pemerintah", "Pemerintah ke RTK", "Masyarakat luar negeri ke RTP", 1, "Pajak badan usaha adalah aliran dana dari RTP ke pemerintah."),

        // POKOK 7 (TopicId = 7: Permintaan, Penawaran & Keseimbangan Pasar)
        QuizQuestion(0, 7, "Hukum permintaan menyatakan bahwa hubungan antara harga barang dengan jumlah barang yang diminta berbanding...", "Lurus (positif)", "Terbalik (negatif), jika kondisi ceteris paribus", "Konstan tanpa dipengaruhi variabel lain", "Eksponensial searah", 1, "Semakin tinggi harga, semakin sedikit jumlah barang yang diminta (ceteris paribus)."),
        QuizQuestion(0, 7, "Asumsi 'Ceteris Paribus' dalam analisis hukum permintaan dan penawaran memiliki makna...", "Harga barang selalu berubah setiap detik", "Faktor-faktor lain selain harga barang itu sendiri dianggap tetap/konstan", "Semua konsumen memiliki pendapatan tanpa batas", "Pemerintah melarang perdagangan bebas", 1, "Ceteris paribus berarti faktor lain dianggap tetap tidak berubah."),
        QuizQuestion(0, 7, "Pergeseran kurva permintaan ke arah kanan menunjukkan terjadinya...", "Penurunan jumlah permintaan pada harga yang sama", "Peningkatan jumlah permintaan yang dipicu kenaikan pendapatan konsumen", "Penurunan biaya produksi barang", "Peningkatan pajak penjualan oleh pemerintah", 1, "Kurva bergeser ke kanan jika ada peningkatan permintaan akibat faktor non-harga."),
        QuizQuestion(0, 7, "Hukum penawaran menyatakan bahwa apabila harga suatu barang mengalami kenaikan, maka produsen akan...", "Mengurangi jumlah barang yang diproduksi", "Menambah jumlah barang yang ditawarkan untuk meraih keuntungan lebih besar", "Menutup pabrik mereka sementara waktu", "Menurunkan kualitas bahan baku", 1, "Harga naik memotivasi produsen meningkatkan pasokan di pasar."),
        QuizQuestion(0, 7, "Titik ekuilibrium (keseimbangan pasar) terjadi pada saat...", "Jumlah permintaan sama dengan jumlah penawaran (Qd = Qs)", "Jumlah penawaran jauh melebihi permintaan", "Harga barang mencapai nilai nol rupiah", "Produsen menetapkan harga setinggi mungkin", 0, "Ekuilibrium adalah titik potong kurva permintaan dan penawaran di mana Qd = Qs."),
        QuizQuestion(0, 7, "Diketahui fungsi permintaan Qd = 80 - 2P dan fungsi penawaran Qs = 20 + 4P. Harga keseimbangan (P) pasar tersebut adalah...", "Rp5", "Rp10", "Rp15", "Rp20", 1, "80 - 2P = 20 + 4P -> 60 = 6P -> P = 10."),
        QuizQuestion(0, 7, "Berdasarkan soal sebelumnya dengan P = 10, berapakah jumlah keseimbangan (Q) di pasar tersebut?", "40 unit", "50 unit", "60 unit", "70 unit", 2, "Q = 80 - 2(10) = 60 unit."),
        QuizQuestion(0, 7, "Jika harga pasar berada di atas harga keseimbangan, maka yang akan terjadi di pasar adalah...", "Kekurangan stok barang (Shortage)", "Kelebihan pasokan barang (Surplus penawaran)", "Kurva penawaran bergeser vertikal", "Jumlah pembeli melonjak drastis", 1, "Harga terlalu tinggi membuat barang menumpuk tidak laku (surplus penawaran)."),
        QuizQuestion(0, 7, "Kenaikan harga bahan bakar minyak (BBM) menyebabkan naiknya biaya transportasi barang. Hal ini akan menyebabkan kurva penawaran barang...", "Bergeser ke kiri atas (penawaran berkurang)", "Bergeser ke kanan bawah (penawaran bertambah)", "Tetap tidak bergerak sama sekali", "Menjadi elastis uniter sempurna", 0, "Kenaikan biaya produksi menggeser kurva penawaran ke kiri."),
        QuizQuestion(0, 7, "Faktor yang menyebabkan pergerakan (movement along the curve) di sepanjang kurva permintaan adalah...", "Perubahan selera konsumen", "Perubahan harga barang itu sendiri", "Perubahan jumlah penduduk", "Kampanye promosi produk", 1, "Pergerakan di sepanjang kurva hanya diakibatkan oleh perubahan harga barang itu sendiri."),

        // POKOK 8 (TopicId = 8: Elastisitas & Struktur Pasar)
        QuizQuestion(0, 8, "Koefisien elastisitas permintaan yang menunjukkan nilai Ed > 1 dikategorikan sebagai permintaan...", "Inelastis", "Elastis", "Elastis uniter", "Inelastis sempurna", 1, "Ed > 1 berarti persentase perubahan jumlah lebih besar dibanding persentase perubahan harga (elastis)."),
        QuizQuestion(0, 8, "Barang kebutuhan pokok seperti beras dan garam umumnya memiliki sifat elastisitas permintaan...", "Elastis (Ed > 1)", "Inelastis (Ed < 1)", "Elastis sempurna (Ed = tak hingga)", "Negatif tak menentu", 1, "Meskipun harga beras naik, konsumen tetap harus membelinya sehingga bersifat inelastis."),
        QuizQuestion(0, 8, "Bentuk kurva permintaan yang bergaris tegak lurus vertikal mencerminkan elastisitas...", "Elastis uniter", "Inelastis sempurna (Ed = 0)", "Elastis sempurna", "Inelastis ganda", 1, "Kurva vertikal menunjukkan kuantitas yang diminta sama sekali tidak berubah berapapun harganya (Ed = 0)."),
        QuizQuestion(0, 8, "Ciri utama dari pasar persaingan sempurna yang membedakannya dari pasar lain adalah...", "Hanya ada satu penjual yang menguasai pasar", "Produk yang diperjualbelikan bersifat homogen dan penjual bertindak sebagai price taker", "Penjual bebas menetapkan harga tanpa batas", "Terdapat hambatan masuk pasar yang sangat tinggi", 1, "Di pasar persaingan sempurna, barang seragam/homogen dan penjual tidak bisa menentukan harga sendiri."),
        QuizQuestion(0, 8, "PT PLN (Persero) di Indonesia merupakan contoh nyata dari bentuk pasar...", "Persaingan sempurna", "Monopoli", "Oligopoli", "Monopsoni beras", 1, "PLN menjadi satu-satunya penyedia transmisi dan distribusi listrik nasional (monopoli negara)."),
        QuizQuestion(0, 8, "Industri semen dan industri provider telekomunikasi seluler di Indonesia yang dikuasai beberapa pemain besar tergolong dalam pasar...", "Monopolistik", "Monopoli murni", "Oligopoli", "Persaingan sempurna bebas", 2, "Pasar oligopoli dikuasai oleh beberapa produsen besar yang saling mempengaruhi."),
        QuizQuestion(0, 8, "Pasar persaingan monopolistik memiliki kemiripan dengan pasar persaingan sempurna karena banyaknya penjual, namun bedanya produknya...", "Wajib dijual dengan harga seragam", "Memiliki diferensiasi merk, kemasan, atau mutu (berbeda corak)", "Hanya diproduksi satu tahun sekali", "Bebas dari pengaruh iklan", 1, "Produk monopolistik terdiferensiasi (berbeda corak) seperti sabun mandi atau pasta gigi."),
        QuizQuestion(0, 8, "Kelemahan pasar monopoli bagi masyarakat konsumen adalah...", "Kualitas barang selalu terlalu tinggi", "Konsumen tidak memiliki pilihan lain dan rentan dirugikan oleh penetapan harga sepihak", "Persaingan harga membuat pengusaha merugi", "Pemerintah tidak memperoleh pemasukan pajak", 1, "Monopoli mengurangi pilihan alternatif konsumen dan memiliki kekuatan pasar tinggi."),
        QuizQuestion(0, 8, "Jika harga naik 10% dan mengakibatkan penurunan jumlah permintaan sebesar 20%, maka nilai elastisitas permintaan barang tersebut adalah...", "0,5 (inelastis)", "1,0 (uniter)", "2,0 (elastis)", "0,1 (sangat rendah)", 2, "Ed = 20% / 10% = 2.0 (karena > 1, maka bersifat elastis)."),
        QuizQuestion(0, 8, "Dalam pasar oligopoli, jika salah satu perusahaan menurunkan harga produk secara drastis, biasanya perusahaan lain akan...", "Menutup pabrik mereka", "Ikut menurunkan harga agar tidak kehilangan pangsa pasar (perang harga)", "Membeli saham kompetitor secara sukarela", "Berhenti beriklan di televisi", 1, "Tindakan menurunkan harga di pasar oligopoli memicu reaksi penurunan harga dari pesaing.")
    )

    // Data sample siswa untuk kelas X.1 sampai X.7
    val sampleStudents = listOf(
        Student(1, "Ahmad Fauzi", "X.1", 1),
        Student(2, "Anisa Rahmawati", "X.1", 2),
        Student(3, "Bagas Pratama", "X.1", 3),
        Student(4, "Dewi Lestari", "X.2", 1),
        Student(5, "Dimas Anggara", "X.2", 2),
        Student(6, "Eka Putri", "X.3", 1),
        Student(7, "Fajar Nugraha", "X.3", 2),
        Student(8, "Gita Permata", "X.4", 1),
        Student(9, "Hendra Saputra", "X.5", 1),
        Student(10, "Indah Kusuma", "X.6", 1),
        Student(11, "Joko Susilo", "X.7", 1),
        Student(12, "Lestari Wulandari", "X.7", 2)
    )

    val sampleQuizResults = listOf(
        QuizResult(0, 1, "Ahmad Fauzi", "X.1", 1, 90, 9, 10, System.currentTimeMillis() - 86400000),
        QuizResult(0, 1, "Ahmad Fauzi", "X.1", 2, 80, 8, 10, System.currentTimeMillis() - 72000000),
        QuizResult(0, 1, "Ahmad Fauzi", "X.1", 3, 100, 10, 10, System.currentTimeMillis() - 60000000),
        QuizResult(0, 2, "Anisa Rahmawati", "X.1", 1, 100, 10, 10, System.currentTimeMillis() - 50000000),
        QuizResult(0, 2, "Anisa Rahmawati", "X.1", 2, 90, 9, 10, System.currentTimeMillis() - 40000000),
        QuizResult(0, 4, "Dewi Lestari", "X.2", 1, 80, 8, 10, System.currentTimeMillis() - 36000000),
        QuizResult(0, 4, "Dewi Lestari", "X.2", 2, 90, 9, 10, System.currentTimeMillis() - 25000000),
        QuizResult(0, 6, "Eka Putri", "X.3", 1, 70, 7, 10, System.currentTimeMillis() - 18000000),
        QuizResult(0, 8, "Gita Permata", "X.4", 1, 100, 10, 10, System.currentTimeMillis() - 10000000),
        QuizResult(0, 11, "Joko Susilo", "X.7", 1, 80, 8, 10, System.currentTimeMillis() - 5000000)
    )

    val sampleGameScores = listOf(
        GameScore(0, 1, "Ahmad Fauzi", "X.1", 850, System.currentTimeMillis() - 50000000),
        GameScore(0, 2, "Anisa Rahmawati", "X.1", 950, System.currentTimeMillis() - 45000000),
        GameScore(0, 4, "Dewi Lestari", "X.2", 780, System.currentTimeMillis() - 30000000),
        GameScore(0, 8, "Gita Permata", "X.4", 920, System.currentTimeMillis() - 15000000),
        GameScore(0, 11, "Joko Susilo", "X.7", 810, System.currentTimeMillis() - 4000000)
    )

    val sampleActivityLogs = listOf(
        ActivityLog(0, "Ahmad Fauzi", "X.1", "KUIS", "Menyelesaikan Kuis 1 (Kelangkaan) dengan nilai 90", System.currentTimeMillis() - 86400000, true),
        ActivityLog(0, "Anisa Rahmawati", "X.1", "KUIS", "Menyelesaikan Kuis 1 dengan nilai sempurna 100", System.currentTimeMillis() - 50000000, true),
        ActivityLog(0, "Dewi Lestari", "X.2", "GAME", "Bermain Game Tantangan Ekonomi Pasar mencapai skor 780", System.currentTimeMillis() - 30000000, false),
        ActivityLog(0, "Gita Permata", "X.4", "KUIS", "Menyelesaikan Kuis 1 dengan nilai 100", System.currentTimeMillis() - 10000000, false),
        ActivityLog(0, "Joko Susilo", "X.7", "GAME", "Bermain Game Tantangan Ekonomi Pasar mencapai skor 810", System.currentTimeMillis() - 4000000, false)
    )
}
