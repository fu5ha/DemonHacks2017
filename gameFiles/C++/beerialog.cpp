
#include "beerwantermenudialog.h"

#include <cassert>
#include <cstdlib>
#include <iostream>
#include <sstream>
#include <stdexcept>

#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Weffc++"
#include <QFile>

#include "beerwantermaindialog.h"
#include "fileio.h"


#pragma GCC diagnostic pop

int ribi::BeerWanterMenuDialog::ExecuteSpecific(
  const std::vector<std::string>&
) noexcept
{
  std::cout
    << std::endl
    << GetAbout().GetFileTitle() << " cannot be run in console mode\n"
    << std::endl;
  return 0;
}

ribi::About ribi::BeerWanterMenuDialog::GetAbout() const noexcept
{
  About a(
    "Richel Bilderbeek",
    "BeerWanter",
    "a simple game",
    "the 19th of March 2016",
    "2005-2016",
    "http://www.richelbilderbeek.nl/GameBeerWanter.htm",
    GetVersion(),
    GetVersionHistory());
  return a;
}

ribi::Help ribi::BeerWanterMenuDialog::GetHelp() const noexcept
{
  return Help(
    GetAbout().GetFileTitle(),
    GetAbout().GetFileDescription(),
    {
      Help::Option('s',"show","show a beer")
    },
    {
      GetAbout().GetFileTitle() + " --show"
    }
  );
}

std::string ribi::BeerWanterMenuDialog::GetVersion() const noexcept
{
  return "8.0";
}

std::vector<std::string>
ribi::BeerWanterMenuDialog::GetVersionHistory() const noexcept
{
  return {
    "2005-11-16: version 1.0: (at that time called) 'Beerwanter 1' was "
      "programmed in C++ using the C++ Builder IDE during a "
      "'Spass programmieren' session, which made BeerWanter "
      "a game for Windows users only",
    "2005-11-18: version 2.0: (at that time called) 'Beerwanter 2' was "
      "programmed after the success of BeerWanter 1.0. "
      "The game was made tougher",
    "2005-11-22: version 3.0: (at that time called) 'Beerwanter 3' was "
      "programmed after the success of BeerWanter 2.0. "
      "The game was made even tougher"
    
  };
}
