-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2023 at 03:46 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tictactoe`
--

-- --------------------------------------------------------

--
-- Table structure for table `tictactoesetting`
--

CREATE TABLE `tictactoesetting` (
  `setting_id` int(11) NOT NULL,
  `game_mode` int(11) NOT NULL,
  `board_size` int(11) NOT NULL,
  `minfo_timer` varchar(11) NOT NULL,
  `minfo_board` varchar(11) NOT NULL,
  `minfo_win` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tictactoesetting`
--

INSERT INTO `tictactoesetting` (`setting_id`, `game_mode`, `board_size`, `minfo_timer`, `minfo_board`, `minfo_win`) VALUES
(1, 0, 0, 'true', 'true', 'true');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tictactoesetting`
--
ALTER TABLE `tictactoesetting`
  ADD PRIMARY KEY (`setting_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tictactoesetting`
--
ALTER TABLE `tictactoesetting`
  MODIFY `setting_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
